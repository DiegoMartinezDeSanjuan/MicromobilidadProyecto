package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.VehicleID;
import micromobility.PMVehicle;
import exceptions.*;
import services.Server;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.QRDecoder;
import services.smartfeatures.UnbondedBTSignal;


import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clase controladora del caso de uso "Realizar desplazamiento".
 * Maneja eventos de entrada desde la interfaz de usuario y canales externos (Bluetooth y Arduino).
 */
public class JourneyRealizeHandler {

    // Atributos
    private Server server;                    // Dependencia del servidor
    private QRDecoder qrDecoder;              // Servicio para decodificar códigos QR
    private ArduinoMicroController arduino;  // Microcontrolador Arduino para interacciones con el vehículo
    private UnbondedBTSignal btSignal; // Nueva dependencia para manejar Bluetooth
    private JourneyService currentJourney;    // El servicio de trayecto actual
    private PMVehicle currentVehicle;       // Vehículo actual asignado

    /**
     * Constructor de JourneyRealizeHandler.
     *
     * @param server     Servidor del sistema.
     * @param qrDecoder  Servicio para decodificar códigos QR.
     * @param arduino    Microcontrolador Arduino.
     */
    public JourneyRealizeHandler(Server server, QRDecoder qrDecoder, ArduinoMicroController arduino, UnbondedBTSignal btSignal) {
        this.server = server;
        this.qrDecoder = qrDecoder;
        this.arduino = arduino;
        this.btSignal = btSignal; // Inicialización de la nueva dependencia
    }

    // Métodos de eventos de entrada

    /**
     * Decodifica el código QR y prepara el sistema para realizar un desplazamiento.
     *
     * @throws ConnectException                Error de conexión.
     * @throws InvalidPairingArgsException     Argumentos inválidos para el emparejamiento.
     * @throws CorruptedImgException           Imagen del código QR corrupta.
     * @throws PMVNotAvailException            El vehículo no está disponible.
     * @throws ProceduralException             Error en la secuencia procedimental.
     */
    public void scanQR(BufferedImage qrImage) throws ConnectException, InvalidPairingArgsException,
            CorruptedImgException, PMVNotAvailException, ProceduralException {

        try {
            // Decodificar el QR para obtener el VehicleID
            VehicleID vehicleID = qrDecoder.getVehicleID(qrImage); // Puede lanzar CorruptedImgException

            if (vehicleID == null) {
                throw new InvalidPairingArgsException("VehicleID no puede ser nulo o inválido.");
            }

            // Obtener el vehículo desde el servidor
            currentVehicle = server.getVehicleByID(vehicleID); // Puede lanzar InvalidPairingArgsException

            // Verificar si el vehículo está disponible
            if (currentVehicle.getState() != PMVState.Available) {
                throw new PMVNotAvailException("El vehículo no está disponible para su uso.");
            }

            // Actualizar el estado del vehículo a "UnderWay"
            currentVehicle.setUnderWay();

            // Registrar el trayecto actual
            currentJourney = new JourneyService(
                    currentVehicle.getLocation(),
                    java.time.LocalDate.now(),
                    java.time.LocalTime.now()
            );

            System.out.println("El trayecto ha comenzado con éxito.");
        } catch (CorruptedImgException | InvalidPairingArgsException | PMVNotAvailException e) {
            throw e; // Relanzar excepciones específicas
        } catch (Exception e) {
            throw new ProceduralException("Error en la secuencia procedimental: " + e.getMessage());
        }
    }


    /**
     * Finaliza el trayecto actual y realiza las actualizaciones necesarias.
     *
     * @throws ConnectException                Error de conexión.
     * @throws InvalidPairingArgsException     Argumentos inválidos para el emparejamiento.
     * @throws PairingNotFoundException        No se encuentra el emparejamiento.
     * @throws ProceduralException             Error en la secuencia procedimental.
     */
    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException,
            PairingNotFoundException, ProceduralException {

        try {
            // Validar que el trayecto actual existe y está en progreso
            if (currentJourney == null || !currentJourney.isInProgress()) {
                throw new ProceduralException("No hay un trayecto en progreso para finalizar.");
            }

            // Validar que el vehículo actual existe y está asociado
            if (currentVehicle == null) {
                throw new PairingNotFoundException("No hay un vehículo asociado para finalizar el trayecto.");
            }

            // Llamada al servidor para registrar la finalización del emparejamiento
            server.stopPairing(
                    currentJourney.getUser(),
                    currentVehicle.getId(),
                    currentJourney.getEndStation(),
                    currentVehicle.getLocation(),
                    LocalDateTime.now(),
                    0, // Velocidad promedio (mock)
                    0, // Distancia recorrida (mock)
                    0, // Duración (mock)
                    BigDecimal.ZERO // Importe calculado (mock)
            );

            // Actualizar el estado del vehículo a Available
            currentVehicle.setAvailb();

            // Finalizar el trayecto actual
            currentJourney.setInProgress(false);

            System.out.println("El trayecto ha finalizado correctamente.");
        } catch (ConnectException | InvalidPairingArgsException | PairingNotFoundException e) {
            throw e; // Relanzar excepciones específicas
        } catch (Exception e) {
            throw new ProceduralException("Error inesperado al finalizar el trayecto: " + e.getMessage());
        }
    }



    /**
     * Emula la recepción del ID de una estación a través del canal Bluetooth.
     *
     * @param stID El ID de la estación.
     * @throws ConnectException Error de conexión.
     */
    public void broadcastStationID(StationID stID) throws ConnectException {
        if (stID == null) {
            throw new IllegalArgumentException("El ID de la estación no puede ser nulo.");
        }

        System.out.println("Simulación: Recibiendo el ID de la estación a través de Bluetooth: " + stID);

        // Llamada al método BTbroadcast de UnbondedBTSignal
        try {
            btSignal.BTbroadcast();
        } catch (ConnectException e) {
            throw new ConnectException("Error de conexión Bluetooth al transmitir el ID de la estación.", e);
        }
    }

    /**
     * Inicia el desplazamiento del vehículo.
     *
     * @throws ConnectException    Error de conexión.
     * @throws ProceduralException Error en la secuencia procedimental.
     */
    public void startDriving() throws ConnectException, ProceduralException {
        try {
            // Validar que hay un vehículo asociado
            if (currentVehicle == null) {
                throw new ProceduralException("No hay un vehículo vinculado para iniciar el desplazamiento.");
            }

            // Validar que el estado del vehículo es NotAvailable
            if (currentVehicle.getState() != PMVState.NotAvailable) {
                throw new ProceduralException("El vehículo no está en estado NotAvailable.");
            }

            // Validar que existe un JourneyService en progreso
            if (currentJourney == null) {
                throw new ProceduralException("No se ha creado una instancia de JourneyService para iniciar el desplazamiento.");
            }

            // Validar conexión Bluetooth
            System.out.println("Verificando conexión Bluetooth...");

            // Actualizar el estado del vehículo a "UnderWay"
            currentVehicle.setUnderWay();

            // Actualizar JourneyService para indicar que el trayecto está en curso
            currentJourney.setInProgress(true);

            System.out.println("El desplazamiento ha comenzado exitosamente.");
        } catch (Exception e) {
            throw new ConnectException("Error inesperado al intentar iniciar el desplazamiento: " + e.getMessage());
        }
    }

    /**
     * Detiene el desplazamiento del vehículo.
     *
     * @throws ConnectException    Error de conexión.
     * @throws ProceduralException Error en la secuencia procedimental.
     */
    public void stopDriving() throws ConnectException, ProceduralException {
        try {
            // Validar que hay un vehículo asociado
            if (currentVehicle == null) {
                throw new ProceduralException("No hay un vehículo vinculado para detener el desplazamiento.");
            }

            // Validar que el estado del vehículo es UnderWay
            if (currentVehicle.getState() != PMVState.UnderWay) {
                throw new ProceduralException("El vehículo no está en marcha para detener el desplazamiento.");
            }

            // Validar que existe un JourneyService en progreso
            if (currentJourney == null || !currentJourney.isInProgress()) {
                throw new ProceduralException("No hay un trayecto en curso para detener.");
            }

            // Validar conexión Bluetooth
            System.out.println("Verificando conexión Bluetooth...");

            // Actualizar JourneyService para indicar que el trayecto no está en curso
            currentJourney.setInProgress(false);

            System.out.println("El desplazamiento ha sido detenido exitosamente.");
        } catch (Exception e) {
            throw new ConnectException("Error inesperado al intentar detener el desplazamiento: " + e.getMessage());
        }
    }

    // Métodos internos

    /**
     * Calcula los valores del trayecto (duración, distancia, velocidad promedio).
     *
     * @param gP   Punto geográfico final del trayecto.
     * @param date Fecha y hora de finalización.
     */
    private void calculateValues(GeographicPoint gP, LocalDateTime date) {
        if (currentJourney == null || !currentJourney.isInProgress()) {
            throw new IllegalStateException("No hay un trayecto en curso para calcular valores.");
        }

        // Calcular duración
        LocalDateTime startDateTime = LocalDateTime.of(
                currentJourney.getStartDate(),
                currentJourney.getStartTime()
        );
        int duration = (int) java.time.Duration.between(startDateTime, date).toMinutes();
        currentJourney.setDuration(duration);

        // Calcular distancia
        GeographicPoint origin = currentJourney.getOriginPoint();
        float distance = calculateDistance(origin, gP);
        currentJourney.setDistance(distance);

        // Calcular velocidad promedio
        float avgSpeed = duration > 0 ? (distance / duration) * 60 : 0;
        currentJourney.setAverageSpeed(avgSpeed);

        System.out.println("Valores calculados: duración = " + duration + " min, distancia = " + distance +
                " km, velocidad promedio = " + avgSpeed + " km/h.");
    }

    /**
     * Calcula la distancia entre dos puntos geográficos.
     *
     * @param start Punto de inicio.
     * @param end   Punto de finalización.
     * @return Distancia en kilómetros.
     */
    private float calculateDistance(GeographicPoint start, GeographicPoint end) {
        double earthRadius = 6371; // Radio de la Tierra en km

        double latDiff = Math.toRadians(end.getLatitude() - start.getLatitude());
        double lonDiff = Math.toRadians(end.getLongitude() - start.getLongitude());

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(start.getLatitude())) * Math.cos(Math.toRadians(end.getLatitude())) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (float) (earthRadius * c);
    }

    /**
     * Calcula el importe correspondiente al trayecto.
     * @param date  Fecha de finalización.
     */
    private void calculateImport(LocalDateTime date) {
        if (currentJourney == null || !currentJourney.isInProgress()) {
            throw new IllegalStateException("No hay un trayecto en curso para calcular el importe.");
        }

        // Validar que los valores necesarios están presentes
        float distance = currentJourney.getDistance();
        float duration = currentJourney.getDuration();
        float avgSpeed = currentJourney.getAverageSpeed();

        if (distance <= 0 || duration <= 0) {
            throw new IllegalArgumentException("La distancia y la duración deben ser mayores a 0 para calcular el importe.");
        }

        // Tarifas base
        float baseRate = 0.5f; // Tarifa base por km
        float timeRate = 0.1f; // Tarifa base por minuto

        // Calcular el importe
        float importValue = (distance * baseRate) + (duration * timeRate);

        // Actualizar el importe en el trayecto actual
        currentJourney.setImportValue(BigDecimal.valueOf(importValue));

        System.out.println("Importe calculado: " + importValue + " EUR (distancia = " + distance + " km, duración = " +
                duration + " min, velocidad promedio = " + avgSpeed + " km/h).");
    }

    // Métodos setter para inyectar dependencias (opcional)
    public void setServer(Server server) {
        this.server = server;
    }

    public void setQrDecoder(QRDecoder qrDecoder) {
        this.qrDecoder = qrDecoder;
    }

    public void setArduino(ArduinoMicroController arduino) {
        this.arduino = arduino;
    }

    public JourneyService getCurrentJourney() {
        return currentJourney;
    }

    public void setCurrentJourney(JourneyService currentJourney) {
        this.currentJourney = currentJourney;
    }

    public PMVehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(PMVehicle currentVehicle) {
        this.currentVehicle = currentVehicle;
    }
}
