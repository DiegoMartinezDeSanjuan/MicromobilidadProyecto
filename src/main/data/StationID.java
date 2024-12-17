package data;

import exceptions.InvalidPairingArgsException;

/**
 * Represents an immutable identifier for a station.
 */
public final class StationID {

    private final String id;

    /**
     * Constructor for StationID with validation.
     *
     * @param id The station identifier.
     * @throws InvalidPairingArgsException If id is null, empty, or invalid.
     */
    public StationID(String id) throws InvalidPairingArgsException {
        if (id == null || id.isEmpty()) {
            throw new InvalidPairingArgsException("StationID cannot be null or empty.");
        }
        if (!id.matches("[A-Za-z0-9]{3,10}")) {
            throw new InvalidPairingArgsException("StationID must be 3 to 10 alphanumeric characters.");
        }
        this.id = id;
    }

    /**
     * Getter for the station identifier.
     *
     * @return The station identifier.
     */
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationID stationID = (StationID) o;
        return id.equals(stationID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "StationID {id='" + id + "'}";
    }
}
