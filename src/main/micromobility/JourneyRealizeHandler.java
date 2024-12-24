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
    public void scanQR(BufferedImage qrImage) throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {
        System.out.println("Iniciando proceso de escaneo de QR...");

        if (qrImage == null) {
            throw new CorruptedImgException("La imagen del QR está corrupta o es nula.");
        }

        try {
            VehicleID vehicleID = qrDecoder.getVehicleID(qrImage);
            if (vehicleID == null) {
                throw new ProceduralException("VehicleID no puede ser nulo o inválido.");
            }

            System.out.println("QR decodificado, VehicleID: " + vehicleID);

            currentVehicle = server.getVehicleByID(vehicleID);
            if (currentVehicle == null) {
                throw new ProceduralException("El vehículo no se encontró en el servidor.");
            }

            System.out.println("Estado inicial del vehículo: " + currentVehicle.getState());

            if (currentVehicle.getState() != PMVState.Available) {
                throw new PMVNotAvailException("El vehículo no está disponible.");
            }

            // Actualización del estado si todo está correcto
            currentVehicle.setNotAvailb();
            System.out.println("Estado del vehículo actualizado a 'NotAvailable'.");
        } catch (CorruptedImgException e) {
            // Propagar la excepción directamente
            throw e;
        } catch (PMVNotAvailException e) {
            throw e;
        } catch (Exception e) {
            // Encapsular cualquier otra excepción no prevista
            throw new ProceduralException("Error durante el escaneo del QR: " + e.getMessage(), e);
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
    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {
        try {
            if (currentJourney == null || !currentJourney.isInProgress()) {
                throw new ProceduralException("No hay un trayecto en progreso para finalizar.");
            }

            if (currentVehicle == null) {
                throw new PairingNotFoundException("No hay un vehículo asociado para finalizar el trayecto.");
            }

            // Calcular valores del trayecto antes de finalizar
            GeographicPoint endPoint = currentVehicle.getLocation();
            if (endPoint == null) {
                throw new ProceduralException("La ubicación del vehículo no está disponible.");
            }

            LocalDateTime endDateTime = LocalDateTime.now();
            calculateValues(endPoint, endDateTime);

            if (currentJourney.getDuration() <= 0) {
                throw new ProceduralException("La duración debe ser mayor a 0.");
            }

            // Calcular el importe del trayecto
            calculateImport(endDateTime);

            if (currentJourney.getImportValue().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ProceduralException("El importe debe ser mayor a 0.");
            }

            // Detener el emparejamiento en el servidor
            server.stopPairing(
                    currentJourney.getUser(),
                    currentVehicle.getId(),
                    currentJourney.getEndStation(),
                    endPoint,
                    endDateTime,
                    currentJourney.getAverageSpeed(),
                    currentJourney.getDistance(),
                    currentJourney.getDuration(),
                    currentJourney.getImportValue()
            );

            // Actualizar el estado del vehículo y del trayecto
            currentVehicle.setAvailb();
            currentJourney.setInProgress(false);

            System.out.println("El trayecto ha finalizado correctamente.");
        } catch (Exception e) {
            throw new ProceduralException("Error inesperado al finalizar el trayecto: " + e.getMessage(), e);
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
        System.out.println("Iniciando desplazamiento...");

        if (currentVehicle == null) {
            throw new ProceduralException("No hay un vehículo vinculado para iniciar el desplazamiento.");
        }

        if (currentVehicle.getState() != PMVState.NotAvailable) {
            throw new ProceduralException("El vehículo no está en estado NotAvailable.");
        }

        if (currentJourney == null) {
            throw new ProceduralException("No se ha creado una instancia de JourneyService para iniciar el desplazamiento.");
        }

        try {
            currentVehicle.setUnderWay();
            currentJourney.setInProgress(true);
            System.out.println("El desplazamiento ha comenzado exitosamente.");
        } catch (Exception e) {
            throw new ProceduralException("Error inesperado al iniciar el desplazamiento: " + e.getMessage(), e);
        }
    }


    /**
     * Detiene el desplazamiento del vehículo.
     *
     * @throws ConnectException    Error de conexión.
     * @throws ProceduralException Error en la secuencia procedimental.
     */
    public void stopDriving() throws ConnectException, ProceduralException {
        if (currentVehicle == null) {
            throw new ProceduralException("No hay un vehículo vinculado para detener el desplazamiento.");
        }

        if (currentVehicle.getState() != PMVState.UnderWay) {
            throw new ProceduralException("El vehículo no está en marcha para detener el desplazamiento.");
        }

        if (currentJourney == null || !currentJourney.isInProgress()) {
            throw new ProceduralException("No hay un trayecto en curso para detener.");
        }

        // Cambiar el estado del vehículo a 'Available'
        currentVehicle.setAvailb();
        currentJourney.setInProgress(false);

        System.out.println("El desplazamiento ha sido detenido exitosamente.");
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
