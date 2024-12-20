package micromobility;

import data.*;
import exceptions.*;
import mocks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class JourneyRealizeHandlerTest {

    private JourneyRealizeHandler handler;
    private MockServer mockServer;
    private MockQRDecoder mockQRDecoder;
    private MockArduinoMicroController mockArduino;
    private MockUnbondedBTSignal mockBTSignal;

    @BeforeEach
    void setUp() {
        mockServer = new MockServer();
        mockQRDecoder = new MockQRDecoder();
        mockArduino = new MockArduinoMicroController();
        mockBTSignal = new MockUnbondedBTSignal();
        handler = new JourneyRealizeHandler(mockServer, mockQRDecoder, mockArduino, mockBTSignal);
    }

    @Test
    void testScanQR_Success() throws Exception {
        BufferedImage mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        VehicleID vehicleID = new VehicleID("V12345");
        PMVehicle vehicle = new PMVehicle(vehicleID, PMVState.Available, new GeographicPoint(41.3851f, 2.1734f));
        mockServer.addVehicle(vehicleID, vehicle);

        handler.scanQR(mockImage);

        assertNotNull(handler.getCurrentJourney());
        assertEquals(PMVState.UnderWay, vehicle.getState());
    }

    @Test
    void testScanQR_CorruptedImage() {
        mockQRDecoder.setSimulateCorruptedImage(true);
        BufferedImage mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        assertThrows(CorruptedImgException.class, () -> handler.scanQR(mockImage));
    }

    @Test
    void testScanQR_VehicleNotAvailable() {
        mockQRDecoder.setSimulateVehicleNotAvailable(true);
        BufferedImage mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        assertThrows(PMVNotAvailException.class, () -> handler.scanQR(mockImage));
    }

    @Test
    void testBroadcastStationID_Success() throws InvalidPairingArgsException {
        StationID stationID = new StationID("S123");

        assertDoesNotThrow(() -> handler.broadcastStationID(stationID));
    }

    @Test
    void testBroadcastStationID_ConnectionFailure() throws InvalidPairingArgsException {
        mockBTSignal.setSimulateConnectionIssue(true);
        StationID stationID = new StationID("S123");

        assertThrows(ConnectException.class, () -> handler.broadcastStationID(stationID));
    }

    @Test
    void testStartDriving_Success() throws Exception {
        BufferedImage mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        VehicleID vehicleID = new VehicleID("V12345");
        PMVehicle vehicle = new PMVehicle(vehicleID, PMVState.NotAvailable, new GeographicPoint(41.3851f, 2.1734f));
        mockServer.addVehicle(vehicleID, vehicle);
        handler.scanQR(mockImage);

        handler.startDriving();

        assertEquals(PMVState.UnderWay, vehicle.getState());
    }

    @Test
    void testStartDriving_NoVehicle() {
        assertThrows(ProceduralException.class, () -> handler.startDriving());
    }

    @Test
    void testStopDriving_Success() throws Exception {
        BufferedImage mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        VehicleID vehicleID = new VehicleID("V12345");
        PMVehicle vehicle = new PMVehicle(vehicleID, PMVState.UnderWay, new GeographicPoint(41.3851f, 2.1734f));
        mockServer.addVehicle(vehicleID, vehicle);
        handler.scanQR(mockImage);

        handler.stopDriving();

        assertFalse(handler.getCurrentJourney().isInProgress());
    }

    @Test
    void testStopDriving_NoVehicle() {
        assertThrows(ProceduralException.class, () -> handler.stopDriving());
    }

    @Test
    void testUnPairVehicle_Success() throws Exception {
        BufferedImage mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        VehicleID vehicleID = new VehicleID("V12345");
        PMVehicle vehicle = new PMVehicle(vehicleID, PMVState.UnderWay, new GeographicPoint(41.3851f, 2.1734f));
        mockServer.addVehicle(vehicleID, vehicle);
        handler.scanQR(mockImage);

        handler.unPairVehicle();

        assertEquals(PMVState.Available, vehicle.getState());
        assertFalse(handler.getCurrentJourney().isInProgress());
    }

    @Test
    void testUnPairVehicle_NoJourney() {
        assertThrows(ProceduralException.class, () -> handler.unPairVehicle());
    }
}
