package data;

import exceptions.InvalidPairingArgsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleIDTest {

    @Test
    void testValidVehicleID() throws InvalidPairingArgsException {
        VehicleID vehicleID = new VehicleID("ABC123");
        assertEquals("ABC123", vehicleID.getId());
        System.out.println("ID válido creado correctamente: " + vehicleID.getId());
    }

    @Test
    void testInvalidVehicleID_Empty() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID("")
        );
        System.out.println("Excepción capturada: " + exception.getMessage());
        assertEquals("VehicleID no puede ser nulo o estar vacío.", exception.getMessage());
    }

    @Test
    void testInvalidVehicleID_Null() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID(null)
        );
        System.out.println("Excepción capturada: " + exception.getMessage());
        assertEquals("VehicleID no puede ser nulo o estar vacío.", exception.getMessage());
    }

    @Test
    void testInvalidVehicleID_TooShort() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID("A1")
        );
        System.out.println("Excepción capturada: " + exception.getMessage());
        assertEquals("VehicleID debe contener entre 5 y 15 caracteres alfanuméricos.", exception.getMessage());
    }

    @Test
    void testInvalidVehicleID_InvalidCharacters() {
        InvalidPairingArgsException exception = assertThrows(
                InvalidPairingArgsException.class,
                () -> new VehicleID("ABC@123")
        );
        System.out.println("Excepción capturada: " + exception.getMessage());
        assertEquals("VehicleID debe contener entre 5 y 15 caracteres alfanuméricos.", exception.getMessage());
    }
}
