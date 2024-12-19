package services;

import data.*;
import exceptions.*;
import micromobility.JourneyService;
import micromobility.PMVehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Interface defining the Server operations for vehicle pairing and management.
 */
public interface Server {

    /**
     * Checks if a PMV (Personal Mobility Vehicle) is available.
     *
     * @param vhID The VehicleID to check availability.
     * @throws PMVNotAvailException If the vehicle is not available.
     * @throws ConnectException     If there is an issue connecting to the server.
     */
    void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException;

    /**
     * Registers a pairing operation.
     *
     * @param user The user account initiating the pairing.
     * @param veh  The vehicle being paired.
     * @param st   The station where the pairing happens.
     * @param loc  The geographic location of the pairing.
     * @param date The date and time of the pairing.
     * @throws InvalidPairingArgsException If any arguments are invalid.
     * @throws ConnectException            If there is an issue connecting to the server.
     */
    void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException;

    /**
     * Stops a pairing operation and logs additional information.
     *
     * @param user The user account ending the pairing.
     * @param veh  The vehicle being unpaired.
     * @param st   The station where the unpairing happens.
     * @param loc  The geographic location of the unpairing.
     * @param date The date and time of the unpairing.
     * @param avSp The average speed during the journey.
     * @param dist The distance covered during the journey.
     * @param dur  The duration of the journey.
     * @param imp  The final amount to be paid for the journey.
     * @throws InvalidPairingArgsException If any arguments are invalid.
     * @throws ConnectException            If there is an issue connecting to the server.
     */
    void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date,
                     float avSp, float dist, int dur, BigDecimal imp)
            throws InvalidPairingArgsException, ConnectException;

    /**
     * Sets a pairing operation for a user and vehicle.
     *
     * @param user The user account.
     * @param veh  The vehicle being paired.
     * @param st   The station where the pairing occurs.
     * @param loc  The geographic location of the pairing.
     * @param date The date and time of the pairing.
     */
    void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date);

    /**
     * Unregisters a service related to a pairing operation.
     *
     * @param service The JourneyService to be unregistered.
     * @throws PairingNotFoundException If the pairing was not found.
     */
    void unPairRegisterService(JourneyService service) throws PairingNotFoundException;

    /**
     * Registers the location of a vehicle.
     *
     * @param veh The vehicle whose location is being registered.
     * @param st  The station where the vehicle is located.
     */
    void registerLocation(VehicleID veh, StationID st);

    PMVehicle getVehicleByID(VehicleID vhID) throws PMVNotAvailException;
}
