package data;

import exceptions.InvalidPairingArgsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleIDTest {

    @Test
    void testValidVehicleID() throws InvalidPairingArgsException {
        VehicleID vehicleID = new VehicleID("ABC123");
        assertEquals("ABC123", vehicleID.getId());
    }

    @Test
    void testInvalidVehicleID_Empty() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID("")
        );
    }

    @Test
    void testInvalidVehicleID_Null() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID(null)
        );
    }

    @Test
    void testInvalidVehicleID_TooShort() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID("A1")
        );
    }

    @Test
    void testInvalidVehicleID_InvalidCharacters() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID("ABC@123")
        );
    }
}
