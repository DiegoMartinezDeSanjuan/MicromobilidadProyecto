package micromobility;

import data.GeographicPoint;
import data.VehicleID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PMVehicleTest {

    private PMVehicle vehicle;

    @BeforeEach
    void setUp() throws Exception {
        VehicleID vehicleID = new VehicleID("V12345");
        vehicle = new PMVehicle(vehicleID, PMVState.Available, new GeographicPoint(41.3851f, 2.1734f));
    }

    @Test
    void testVehicleInitialization() throws Exception {
        assertEquals("V12345", vehicle.getId().getId());
        assertEquals(PMVState.Available, vehicle.getState());
        assertEquals(41.3851f, vehicle.getLocation().getLatitude());
        assertEquals(2.1734f, vehicle.getLocation().getLongitude());
    }

    @Test
    void testSetNotAvailable() {
        vehicle.setNotAvailb();
        assertEquals(PMVState.NotAvailable, vehicle.getState());
    }

    @Test
    void testSetUnderWay() {
        vehicle.setUnderWay();
        assertEquals(PMVState.UnderWay, vehicle.getState());
    }

    @Test
    void testSetAvailable() {
        vehicle.setAvailb();
        assertEquals(PMVState.Available, vehicle.getState());
    }

    @Test
    void testSetLocation() throws Exception {
        GeographicPoint newLocation = new GeographicPoint(41.4036f, 2.1744f); // Sagrada Familia
        vehicle.setLocation(newLocation);

        assertEquals(41.4036f, vehicle.getLocation().getLatitude());
        assertEquals(2.1744f, vehicle.getLocation().getLongitude());
    }
}
