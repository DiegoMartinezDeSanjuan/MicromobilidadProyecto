package micromobility;

import data.GeographicPoint;

/**
 * Representa un Vehículo de Micromovilidad (PMV) con su estado y ubicación.
 */
public class PMVehicle {

    private PMVState state; // Estado actual del vehículo
    private GeographicPoint location; // Ubicación actual del vehículo

    /**
     * Constructor de PMVehicle.
     *
     * @param initialState    El estado inicial del vehículo.
     * @param initialLocation La ubicación inicial del vehículo.
     */
    public PMVehicle(PMVState initialState, GeographicPoint initialLocation) {
        this.state = initialState;
        this.location = initialLocation;
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
     * @param newLocation La nueva ubicación del vehículo.
     */
    public void setLocation(GeographicPoint newLocation) {
        this.location = newLocation;
    }
}
