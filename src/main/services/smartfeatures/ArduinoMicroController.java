package services.smartfeatures;

import exceptions.ConnectException;
import exceptions.PMVNotAvailException;
import exceptions.ProceduralException;

/**
 * Interface representing the Arduino software for vehicle microcontrollers.
 * Handles actions like start, stop, and managing the Bluetooth connection.
 */
public interface ArduinoMicroController {

    /**
     * Establishes the Bluetooth connection between the vehicle and the smartphone.
     *
     * @throws ConnectException If the Bluetooth connection fails.
     */
    void setBTconnection() throws ConnectException;

    /**
     * Represents the action of starting to drive the vehicle.
     *
     * @throws PMVNotAvailException If there is a physical issue preventing the vehicle from starting.
     * @throws ConnectException If there is a Bluetooth connection failure.
     * @throws ProceduralException If there is an unexpected issue in the process.
     */
    void startDriving() throws PMVNotAvailException, ConnectException, ProceduralException;

    /**
     * Represents the action of stopping the vehicle by detecting continuous braking.
     *
     * @throws PMVNotAvailException If there is a physical issue with the braking system.
     * @throws ConnectException If there is a Bluetooth connection failure.
     * @throws ProceduralException If there is an unexpected issue in the process.
     */
    void stopDriving() throws PMVNotAvailException, ConnectException, ProceduralException;

    /**
     * Undoes the Bluetooth connection between the vehicle and the smartphone.
     */
    void undoBTconnection();
}
