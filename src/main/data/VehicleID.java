package data;

import exceptions.InvalidPairingArgsException;
import java.util.Objects;

/**
 * Representa un identificador inmutable para un vehículo.
 */
public final class VehicleID {

    private final String id;

    /**
     * Constructor de VehicleID con validación.
     *
     * @param id El identificador del vehículo.
     * @throws InvalidPairingArgsException Si el ID es nulo, vacío o no válido.
     */
    public VehicleID(String id) throws InvalidPairingArgsException {
        validarId(id);
        this.id = id;
    }

    /**
     * Obtiene el identificador del vehículo.
     *
     * @return El identificador del vehículo.
     */
    public String getId() {
        return id;
    }

    /**
     * Valida el identificador del vehículo.
     *
     * @param id El identificador a validar.
     * @throws InvalidPairingArgsException Si el ID no cumple con los requisitos.
     */
    private void validarId(String id) throws InvalidPairingArgsException {
        if (id == null || id.isEmpty()) {
            throw new InvalidPairingArgsException("El VehicleID no puede ser nulo o estar vacío.");
        }
        if (!id.matches("[A-Za-z0-9]{5,15}")) {
            throw new InvalidPairingArgsException("El VehicleID debe contener entre 5 y 15 caracteres alfanuméricos.");
        }
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
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VehicleID {id='" + id + "'}";
    }
}
