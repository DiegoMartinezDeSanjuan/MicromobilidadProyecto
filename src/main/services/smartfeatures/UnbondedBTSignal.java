package services.smartfeatures;

import exceptions.ConnectException;

/**
 * Interfaz que representa servicios externos relacionados con la transmisión de datos vía Bluetooth.
 */
public interface UnbondedBTSignal {

    /**
     * Emite continuamente el ID de la estación a través del canal Bluetooth.
     * Este método se utiliza para sincronizar información entre dispositivos cercanos.
     *
     * @throws ConnectException Si ocurre un fallo en la conexión Bluetooth.
     */
    void BTbroadcast() throws ConnectException;
}
