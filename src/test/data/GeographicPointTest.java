package data;

import exceptions.InvalidPairingArgsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeographicPointTest {

    @Test
    void testValidGeographicPoint() throws InvalidPairingArgsException {
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        assertEquals(41.3851f, point.getLatitude());
        assertEquals(2.1734f, point.getLongitude());
    }

    @Test
    void testInvalidLatitude() {
        assertThrows(InvalidPairingArgsException.class, () -> new GeographicPoint(-91f, 2.1734f));
        assertThrows(InvalidPairingArgsException.class, () -> new GeographicPoint(91f, 2.1734f));
    }

    @Test
    void testInvalidLongitude() {
        assertThrows(InvalidPairingArgsException.class, () -> new GeographicPoint(41.3851f, -181f));
        assertThrows(InvalidPairingArgsException.class, () -> new GeographicPoint(41.3851f, 181f));
    }

    @Test
    void testEqualsAndHashCode() throws InvalidPairingArgsException {
        GeographicPoint point1 = new GeographicPoint(41.3851f, 2.1734f);
        GeographicPoint point2 = new GeographicPoint(41.3851f, 2.1734f);
        GeographicPoint point3 = new GeographicPoint(40.7128f, -74.006f);

        assertEquals(point1, point2);
        assertNotEquals(point1, point3);
        assertEquals(point1.hashCode(), point2.hashCode());
        assertNotEquals(point1.hashCode(), point3.hashCode());
    }

    @Test
    void testToString() throws InvalidPairingArgsException {
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        assertEquals("GeographicPoint {latitude=41.3851, longitude=2.1734}", point.toString());
    }
}
