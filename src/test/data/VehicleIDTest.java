package data;

import exceptions.InvalidPairingArgsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test para la clase VehicleID.
 * Verifica la creación, validaciones y restricciones en los identificadores de vehículos.
 */
class VehicleIDTest {

    /**
     * Verifica que se pueda crear un VehicleID válido.
     */
    @Test
    void testCreateValidVehicleID() throws InvalidPairingArgsException {
        VehicleID vehicleID = new VehicleID("ABC123");
        assertEquals("ABC123", vehicleID.getId(), "El ID del vehículo debería coincidir con el proporcionado.");
    }

    /**
     * Verifica que los VehicleID válidos en los límites sean aceptados.
     */
    @Test
    void testValidVehicleIDBoundaryValues() throws InvalidPairingArgsException {
        VehicleID vehicleIDMin = new VehicleID("ABCDE"); // Longitud mínima válida
        VehicleID vehicleIDMax = new VehicleID("ABCDEFGHIJKLMNO"); // Longitud máxima válida

        assertEquals("ABCDE", vehicleIDMin.getId(), "El ID del vehículo con longitud mínima debería ser aceptado.");
        assertEquals("ABCDEFGHIJKLMNO", vehicleIDMax.getId(), "El ID del vehículo con longitud máxima debería ser aceptado.");
    }

    /**
     * Verifica que un VehicleID vacío lance una excepción.
     */
    @Test
    void testVehicleID_EmptyThrowsException() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID(""),
                "Un ID de vehículo vacío debería lanzar una excepción."
        );
    }

    /**
     * Verifica que un VehicleID nulo lance una excepción.
     */
    @Test
    void testVehicleID_NullThrowsException() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID(null),
                "Un ID de vehículo nulo debería lanzar una excepción."
        );
    }

    /**
     * Verifica que un VehicleID demasiado corto lance una excepción.
     */
    @Test
    void testVehicleID_TooShortThrowsException() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID("A1"),
                "Un ID de vehículo demasiado corto debería lanzar una excepción."
        );
    }

    /**
     * Verifica que un VehicleID con caracteres inválidos lance una excepción.
     */
    @Test
    void testVehicleID_InvalidCharactersThrowsException() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID("ABC@123"),
                "Un ID de vehículo con caracteres inválidos debería lanzar una excepción."
        );
    }
}
