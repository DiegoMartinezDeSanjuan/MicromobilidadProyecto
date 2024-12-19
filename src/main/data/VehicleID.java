package data;

import exceptions.InvalidPairingArgsException;

/**
 * Represents an immutable identifier for a vehicle.
 */
public final class VehicleID {

    private final String id;

    /**
     * Constructor for VehicleID with validation.
     *
     * @param id The vehicle identifier.
     * @throws InvalidPairingArgsException Si id es null, vacio, or invalido.
     */
    public VehicleID(String id) throws InvalidPairingArgsException {
        if (id == null || id.isEmpty()) {
            throw new InvalidPairingArgsException("VehicleID no puede ser nulo o estar vacío.");
        }
        if (!id.matches("[A-Za-z0-9]{5,15}")) {
            throw new InvalidPairingArgsException("VehicleID debe contener entre 5 y 15 caracteres alfanuméricos.");
        }
        this.id = id;
    }

    /**
     * Getter for the vehicle identifier.
     *
     * @return The vehicle identifier.
     */
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleID vehicleID = (VehicleID) o;
        return id.equals(vehicleID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "VehicleID {id='" + id + "'}";
    }
}
