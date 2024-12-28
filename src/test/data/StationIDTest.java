package data;

import exceptions.InvalidPairingArgsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test para la clase StationID.
 * Verifica la creación, validaciones y restricciones de identificadores de estaciones.
 */
class StationIDTest {

    /**
     * Verifica que se pueda crear un StationID válido.
     */
    @Test
    void testCreateValidStationID() throws InvalidPairingArgsException {
        StationID stationID = new StationID("ST123");
        assertEquals("ST123", stationID.getId(), "El ID de la estación debería coincidir con el proporcionado.");
    }

    /**
     * Verifica que los StationID válidos en los límites aceptados se creen correctamente.
     */
    @Test
    void testValidStationID_BoundaryValues() throws InvalidPairingArgsException {
        StationID stationID1 = new StationID("ST001"); // Mínimo aceptable
        StationID stationID2 = new StationID("ST99999"); // Máximo aceptable

        assertEquals("ST001", stationID1.getId(), "El ID de la estación mínima debería ser válido.");
        assertEquals("ST99999", stationID2.getId(), "El ID de la estación máxima debería ser válido.");
    }

    /**
     * Verifica que un StationID vacío lance una excepción.
     */
    @Test
    void testStationID_EmptyThrowsException() {
        assertThrows(InvalidPairingArgsException.class, () -> new StationID(""), "Un ID vacío debería lanzar una excepción.");
    }

    /**
     * Verifica que un StationID nulo lance una excepción.
     */
    @Test
    void testStationID_NullThrowsException() {
        assertThrows(InvalidPairingArgsException.class, () -> new StationID(null), "Un ID nulo debería lanzar una excepción.");
    }

    /**
     * Verifica que un StationID demasiado corto lance una excepción.
     */
    @Test
    void testStationID_TooShortThrowsException() {
        assertThrows(InvalidPairingArgsException.class, () -> new StationID("ST"), "Un ID demasiado corto debería lanzar una excepción.");
    }

    /**
     * Verifica que un StationID con caracteres inválidos lance una excepción.
     */
    @Test
    void testStationID_InvalidCharactersThrowsException() {
        assertThrows(InvalidPairingArgsException.class, () -> new StationID("ST#123"), "Un ID con caracteres inválidos debería lanzar una excepción.");
    }
}
