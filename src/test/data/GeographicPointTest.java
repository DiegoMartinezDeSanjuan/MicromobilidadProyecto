package data;

import exceptions.InvalidPairingArgsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test de la clase GeographicPoint.
 * Verifica la creación, validaciones y funcionalidad de la clase.
 */
class GeographicPointTest {

    /**
     * Verifica que se puedan crear instancias válidas de GeographicPoint.
     */
    @Test
    void testCreateValidGeographicPoint() throws InvalidPairingArgsException {
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        assertEquals(41.3851f, point.getLatitude(), "La latitud no coincide con la esperada.");
        assertEquals(2.1734f, point.getLongitude(), "La longitud no coincide con la esperada.");
    }

    /**
     * Verifica que se lancen excepciones al usar latitudes inválidas.
     */
    @Test
    void testLatitudeOutOfRangeThrowsException() {
        assertThrows(InvalidPairingArgsException.class, () -> new GeographicPoint(-91f, 2.1734f));
        assertThrows(InvalidPairingArgsException.class, () -> new GeographicPoint(91f, 2.1734f));
    }

    /**
     * Verifica que se lancen excepciones al usar longitudes inválidas.
     */
    @Test
    void testLongitudeOutOfRangeThrowsException() {
        assertThrows(InvalidPairingArgsException.class, () -> new GeographicPoint(41.3851f, -181f));
        assertThrows(InvalidPairingArgsException.class, () -> new GeographicPoint(41.3851f, 181f));
    }

    /**
     * Verifica que las latitudes y longitudes en los límites sean válidas.
     */
    @Test
    void testBoundaryValuesAreValid() throws InvalidPairingArgsException {
        GeographicPoint point1 = new GeographicPoint(-90f, 0f); // Latitud mínima
        GeographicPoint point2 = new GeographicPoint(90f, 0f);  // Latitud máxima
        GeographicPoint point3 = new GeographicPoint(0f, -180f); // Longitud mínima
        GeographicPoint point4 = new GeographicPoint(0f, 180f);  // Longitud máxima

        assertEquals(-90f, point1.getLatitude());
        assertEquals(90f, point2.getLatitude());
        assertEquals(-180f, point3.getLongitude());
        assertEquals(180f, point4.getLongitude());
    }

    /**
     * Verifica los métodos equals y hashCode.
     */
    @Test
    void testEqualsAndHashCode() throws InvalidPairingArgsException {
        GeographicPoint point1 = new GeographicPoint(41.3851f, 2.1734f);
        GeographicPoint point2 = new GeographicPoint(41.3851f, 2.1734f);
        GeographicPoint point3 = new GeographicPoint(40.7128f, -74.006f);

        assertEquals(point1, point2, "Los puntos deberían ser iguales.");
        assertNotEquals(point1, point3, "Los puntos deberían ser diferentes.");
        assertEquals(point1.hashCode(), point2.hashCode(), "Los hashCode deberían coincidir.");
        assertNotEquals(point1.hashCode(), point3.hashCode(), "Los hashCode deberían ser diferentes.");
    }

    /**
     * Verifica que el método toString devuelva la representación esperada.
     */
    @Test
    void testToString() throws InvalidPairingArgsException {
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        assertEquals("GeographicPoint {latitude=41.3851, longitude=2.1734}", point.toString(), "El formato del toString no es el esperado.");
    }
}
