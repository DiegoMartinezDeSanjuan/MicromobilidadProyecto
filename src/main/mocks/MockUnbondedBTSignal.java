package mocks;

import exceptions.ConnectException;
import services.smartfeatures.UnbondedBTSignal;

/**
 * Mock para la interfaz UnbondedBTSignal.
 * Simula el comportamiento del canal Bluetooth predeterminado para pruebas.
 */
public class MockUnbondedBTSignal implements UnbondedBTSignal {

    private boolean simulateConnectionIssue = false; // Simula problemas de conexión

    /**
     * Simula la emisión del ID de la estación a través del canal Bluetooth.
     *
     * @throws ConnectException Si se simula un problema de conexión.
     */
    @Override
    public void BTbroadcast() throws ConnectException {
        if (simulateConnectionIssue) {
            throw new ConnectException("Error de conexión en el canal Bluetooth.");
        }
        System.out.println("Mock: Emisión del ID de la estación realizada correctamente.");
    }

    /**
     * Configura si se debe simular un problema de conexión en el canal Bluetooth.
     *
     * @param simulateIssue true para simular un problema de conexión, false para normalidad.
     * @throws IllegalArgumentException Si el valor proporcionado es inválido.
     */
    public void setSimulateConnectionIssue(boolean simulateIssue) {
        this.simulateConnectionIssue = simulateIssue;
    }
}
