package data;

import exceptions.InvalidPairingArgsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StationIDTest {

    @Test
    void testValidStationID() throws InvalidPairingArgsException {
        StationID stationID = new StationID("ST123");
        assertEquals("ST123", stationID.getId());
    }

    @Test
    void testInvalidStationID_Empty() {
        assertThrows(InvalidPairingArgsException.class, () -> new StationID(""));
    }

    @Test
    void testInvalidStationID_Null() {
        assertThrows(InvalidPairingArgsException.class, () -> new StationID(null));
    }

    @Test
    void testInvalidStationID_TooShort() {
        assertThrows(InvalidPairingArgsException.class, () -> new StationID("ST"));
    }

    @Test
    void testInvalidStationID_InvalidCharacters() {
        assertThrows(InvalidPairingArgsException.class, () -> new StationID("ST#123"));
    }
}
