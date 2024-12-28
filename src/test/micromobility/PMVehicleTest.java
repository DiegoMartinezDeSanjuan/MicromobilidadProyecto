package micromobility;

import data.GeographicPoint;
import data.VehicleID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test para la clase PMVehicle.
 * Verifica la inicialización, actualización de estado y manejo de ubicación de un vehículo.
 */
class PMVehicleTest {

    private PMVehicle vehicle;

    /**
     * Configuración inicial antes de cada test.
     *
     * @throws Exception Si ocurre un error en la configuración inicial.
     */
    @BeforeEach
    void setUp() throws Exception {
        VehicleID vehicleID = new VehicleID("V12345");
        vehicle = new PMVehicle(vehicleID, PMVState.Available, new GeographicPoint(41.3851f, 2.1734f));
    }

    /**
     * Verifica que el vehículo se inicialice correctamente con los valores proporcionados.
     */
    @Test
    void testVehicleInitialization() throws Exception {
        assertEquals("V12345", vehicle.getId().getId(), "El ID del vehículo no coincide.");
        assertEquals(PMVState.Available, vehicle.getState(), "El estado inicial del vehículo no es 'Available'.");
        assertEquals(41.3851f, vehicle.getLocation().getLatitude(), "La latitud del vehículo no coincide.");
        assertEquals(2.1734f, vehicle.getLocation().getLongitude(), "La longitud del vehículo no coincide.");
    }

    /**
     * Verifica que el estado del vehículo se pueda actualizar a 'NotAvailable'.
     */
    @Test
    void testSetNotAvailable() {
        vehicle.setNotAvailb();
        assertEquals(PMVState.NotAvailable, vehicle.getState(), "El estado del vehículo debería ser 'NotAvailable'.");
    }

    /**
     * Verifica que el estado del vehículo se pueda actualizar a 'UnderWay'.
     */
    @Test
    void testSetUnderWay() {
        vehicle.setUnderWay();
        assertEquals(PMVState.UnderWay, vehicle.getState(), "El estado del vehículo debería ser 'UnderWay'.");
    }

    /**
     * Verifica que el estado del vehículo se pueda actualizar a 'Available'.
     */
    @Test
    void testSetAvailable() {
        vehicle.setAvailb();
        assertEquals(PMVState.Available, vehicle.getState(), "El estado del vehículo debería ser 'Available'.");
    }

    /**
     * Verifica que se pueda actualizar la ubicación del vehículo.
     */
    @Test
    void testSetLocation() throws Exception {
        GeographicPoint newLocation = new GeographicPoint(41.4036f, 2.1744f); // Sagrada Familia
        vehicle.setLocation(newLocation);

        assertEquals(41.4036f, vehicle.getLocation().getLatitude(), "La nueva latitud del vehículo no coincide.");
        assertEquals(2.1744f, vehicle.getLocation().getLongitude(), "La nueva longitud del vehículo no coincide.");
    }

    /**
     * Verifica que la asignación de una ubicación nula lance una excepción.
     */
    @Test
    void testSetNullLocation() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.setLocation(null), "Asignar una ubicación nula debería lanzar una excepción.");
    }

    /**
     * Verifica que un VehicleID nulo lance una excepción al inicializar el vehículo.
     */
    @Test
    void testInitializeWithNullVehicleID() {
        assertThrows(IllegalArgumentException.class, () -> new PMVehicle(null, PMVState.Available, new GeographicPoint(41.3851f, 2.1734f)), "Un VehicleID nulo debería lanzar una excepción.");
    }

    /**
     * Verifica que una ubicación inicial nula lance una excepción al inicializar el vehículo.
     */
    @Test
    void testInitializeWithNullLocation() {
        assertThrows(IllegalArgumentException.class, () -> new PMVehicle(new VehicleID("V12345"), PMVState.Available, null), "Una ubicación inicial nula debería lanzar una excepción.");
    }
}
