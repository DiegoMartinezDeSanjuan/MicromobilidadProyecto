package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.VehicleID;
import micromobility.PMVehicle;
import exceptions.*;
import services.Server;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.QRDecoder;


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
    private ArduinoMicroController arduino;   // Microcontrolador Arduino para interacciones con el vehículo
    private JourneyService currentJourney;    // El servicio de trayecto actual
    private PMVehicle currentVehicle;       // Vehículo actual asignado

    /**
     * Constructor de JourneyRealizeHandler.
     *
     * @param server     Servidor del sistema.
     * @param qrDecoder  Servicio para decodificar códigos QR.
     * @param arduino    Microcontrolador Arduino.
     */
    public JourneyRealizeHandler(Server server, QRDecoder qrDecoder, ArduinoMicroController arduino) {
        this.server = server;
        this.qrDecoder = qrDecoder;
        this.arduino = arduino;
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
        // Lógica para gestionar el ID de la estación
    }

    /**
     * Inicia el desplazamiento del vehículo.
     *
     * @throws ConnectException    Error de conexión.
     * @throws ProceduralException Error en la secuencia procedimental.
     */
    public void startDriving() throws ConnectException, ProceduralException {
        // Lógica para iniciar el desplazamiento
    }

    /**
     * Detiene el desplazamiento del vehículo.
     *
     * @throws ConnectException    Error de conexión.
     * @throws ProceduralException Error en la secuencia procedimental.
     */
    public void stopDriving() throws ConnectException, ProceduralException {
        // Lógica para detener el desplazamiento
    }

    // Métodos internos

    /**
     * Calcula los valores del trayecto (duración, distancia, velocidad promedio).
     *
     * @param gP   Punto geográfico final del trayecto.
     * @param date Fecha y hora de finalización.
     */
    private void calculateValues(GeographicPoint gP, LocalDateTime date) {
        // Cálculo de distancia, duración y velocidad promedio
    }

    /**
     * Calcula el importe correspondiente al trayecto.
     *
     * @param dis   Distancia recorrida.
     * @param dur   Duración del trayecto.
     * @param avSp  Velocidad promedio.
     * @param date  Fecha de finalización.
     */
    private void calculateImport(float dis, int dur, float avSp, LocalDateTime date) {
        // Cálculo del importe del servicio
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
}
