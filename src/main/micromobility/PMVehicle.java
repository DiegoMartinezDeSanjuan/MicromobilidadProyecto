package micromobility;

import data.GeographicPoint;
import data.VehicleID;

/**
 * Representa un Vehículo de Micromovilidad (PMV) con su estado y ubicación.
 */
public class PMVehicle {

    private final VehicleID id;              // Identificador único del vehículo
    private PMVState state;                  // Estado actual del vehículo
    private GeographicPoint location;        // Ubicación actual del vehículo

    /**
     * Constructor de PMVehicle.
     *
     * @param id              Identificador único del vehículo. No puede ser nulo.
     * @param initialState    El estado inicial del vehículo. No puede ser nulo.
     * @param initialLocation La ubicación inicial del vehículo. No puede ser nula.
     * @throws IllegalArgumentException Si algún parámetro es nulo.
     */
    public PMVehicle(VehicleID id, PMVState initialState, GeographicPoint initialLocation) {
        if (id == null) {
            throw new IllegalArgumentException("El identificador del vehículo no puede ser nulo.");
        }
        if (initialState == null) {
            throw new IllegalArgumentException("El estado inicial no puede ser nulo.");
        }
        if (initialLocation == null) {
            throw new IllegalArgumentException("La ubicación inicial no puede ser nula.");
        }
        this.id = id;
        this.state = initialState;
        this.location = initialLocation;
    }

    /**
     * Obtiene el identificador único del vehículo.
     *
     * @return El identificador del vehículo.
     */
    public VehicleID getId() {
        return id;
    }

    /**
     * Obtiene el estado actual del vehículo.
     *
     * @return El estado actual del vehículo.
     */
    public PMVState getState() {
        return state;
    }

    /**
     * Obtiene la ubicación actual del vehículo.
     *
     * @return La ubicación actual del vehículo.
     */
    public GeographicPoint getLocation() {
        return location;
    }

    /**
     * Cambia el estado del vehículo a No disponible.
     */
    public void setNotAvailb() {
        this.state = PMVState.NotAvailable;
    }

    /**
     * Cambia el estado del vehículo a En marcha.
     */
    public void setUnderWay() {
        this.state = PMVState.UnderWay;
    }

    /**
     * Cambia el estado del vehículo a Disponible.
     */
    public void setAvailb() {
        this.state = PMVState.Available;
    }

    /**
     * Actualiza la ubicación del vehículo.
     *
     * @param newLocation La nueva ubicación del vehículo. No puede ser nula.
     * @throws IllegalArgumentException Si la nueva ubicación es nula.
     */
    public void setLocation(GeographicPoint newLocation) {
        if (newLocation == null) {
            throw new IllegalArgumentException("La nueva ubicación no puede ser nula.");
        }
        this.location = newLocation;
    }

    @Override
    public String toString() {
        return "PMVehicle{" +
                "id=" + id +
                ", state=" + state +
                ", location=" + location +
                '}';
    }
}
