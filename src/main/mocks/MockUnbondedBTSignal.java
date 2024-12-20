package mocks;

import exceptions.ConnectException;
import services.smartfeatures.UnbondedBTSignal;

/**
 * Mock para la interfaz UnbondedBTSignal.
 * Simula el comportamiento del canal Bluetooth predeterminado para pruebas.
 */
public class MockUnbondedBTSignal implements UnbondedBTSignal {

    private boolean simulateConnectionIssue = false; // Simula problemas de conexión

    @Override
    public void BTbroadcast() throws ConnectException {
        if (simulateConnectionIssue) {
            throw new ConnectException("Error de conexión en el canal Bluetooth.");
        }
        System.out.println("Mock: Emisión del ID de la estación realizada correctamente.");
    }

    public void setSimulateConnectionIssue(boolean simulateIssue) {
        this.simulateConnectionIssue = simulateIssue;
    }
}
