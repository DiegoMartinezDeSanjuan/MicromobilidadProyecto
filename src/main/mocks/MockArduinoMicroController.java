package mocks;

import exceptions.ConnectException;
import exceptions.PMVNotAvailException;
import exceptions.ProceduralException;
import services.smartfeatures.ArduinoMicroController;

/**
 * Implementación mock de la interfaz ArduinoMicroController.
 * Simula el comportamiento del microcontrolador Arduino para pruebas.
 */
public class MockArduinoMicroController implements ArduinoMicroController {

    private boolean btConnectionEstablished = false; // Simula si la conexión Bluetooth está establecida
    private boolean drivingStarted = false; // Simula si el vehículo está en marcha

    @Override
    public void setBTconnection() throws ConnectException {
        if (!simulateBTConnection()) {
            throw new ConnectException("Fallo al establecer la conexión Bluetooth.");
        }
        btConnectionEstablished = true;
        System.out.println("Mock: Conexión Bluetooth establecida.");
    }

    @Override
    public void startDriving() throws PMVNotAvailException, ConnectException, ProceduralException {
        if (!btConnectionEstablished) {
            throw new ConnectException("La conexión Bluetooth no está establecida.");
        }
        if (simulatePhysicalIssue()) {
            throw new PMVNotAvailException("Problema físico que impide que el vehículo arranque.");
        }
        if (simulateUnexpectedProcessIssue()) {
            throw new ProceduralException("Problema inesperado en el procedimiento.");
        }
        drivingStarted = true;
        System.out.println("Mock: El vehículo ha comenzado a conducir.");
    }

    @Override
    public void stopDriving() throws PMVNotAvailException, ConnectException, ProceduralException {
        if (!btConnectionEstablished) {
            throw new ConnectException("La conexión Bluetooth no está establecida.");
        }
        if (!drivingStarted) {
            throw new ProceduralException("El vehículo no está actualmente en marcha.");
        }
        if (simulatePhysicalIssue()) {
            throw new PMVNotAvailException("Problema físico con el sistema de frenos.");
        }
        if (simulateUnexpectedProcessIssue()) {
            throw new ProceduralException("Problema inesperado en el procedimiento al detenerse.");
        }
        drivingStarted = false;
        System.out.println("Mock: El vehículo ha dejado de conducir.");
    }

    @Override
    public void undoBTconnection() {
        btConnectionEstablished = false;
        System.out.println("Mock: Conexión Bluetooth deshecha.");
    }

    // Simula el comportamiento de la conexión Bluetooth
    private boolean simulateBTConnection() {
        return true; // Siempre tiene éxito para simplificar; modificar si es necesario
    }

    // Simula un problema físico en el vehículo
    private boolean simulatePhysicalIssue() {
        return false; // Siempre falso; modificar para simular fallos
    }

    // Simula un problema inesperado en el procedimiento
    private boolean simulateUnexpectedProcessIssue() {
        return false; // Siempre falso; modificar para simular fallos
    }
}
