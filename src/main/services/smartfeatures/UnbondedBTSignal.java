package services.smartfeatures;

import exceptions.ConnectException;

/**
 * External services involved in the functioning of some features.
 */
public interface UnbondedBTSignal {

    /**
     * Broadcasts the station ID via Bluetooth continuously.
     *
     * @throws ConnectException If there is a failure in the Bluetooth connection.
     */
    void BTbroadcast() throws ConnectException;
}
