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

        // Crear un VehicleID y un vehículo en estado Available
        VehicleID vehicleID = new VehicleID("V12345");
        PMVehicle vehicle = new PMVehicle(vehicleID, PMVState.Available, new GeographicPoint(41.3851f, 2.1734f));

        // Registrar el vehículo en el servidor mock
        mockServer.addVehicle(vehicleID, vehicle);

        // Configurar el QRDecoder para devolver el mismo VehicleID
        mockQRDecoder.setSimulatedVehicleID(vehicleID);

        // Escanear el QR
        handler.scanQR(mockImage);

        // Verificar que el estado del vehículo ha cambiado a NotAvailable
        assertEquals(PMVState.NotAvailable, vehicle.getState());
    }



    @Test
    void testScanQR_CorruptedImage() {
        mockQRDecoder.setSimulateCorruptedImage(true);
        BufferedImage mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        assertThrows(CorruptedImgException.class, () -> handler.scanQR(mockImage));
    }

    @Test
    void testScanQR_VehicleNotAvailable() throws Exception {
        // Configurar un VehicleID y un vehículo en estado NotAvailable
        VehicleID vehicleID = new VehicleID("V12345");
        PMVehicle unavailableVehicle = new PMVehicle(vehicleID, PMVState.NotAvailable, new GeographicPoint(41.3851f, 2.1734f));

        // Registrar el vehículo en el servidor mock
        mockServer.addVehicle(vehicleID, unavailableVehicle);

        // Configurar el QRDecoder para devolver el mismo VehicleID
        mockQRDecoder.setSimulatedVehicleID(vehicleID);

        // Crear una imagen mock para simular el escaneo de un QR
        BufferedImage mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        // Validar que se lanza PMVNotAvailException al escanear el QR
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

        // Crear un VehicleID y un vehículo en estado Available
        VehicleID vehicleID = new VehicleID("V12345");
        PMVehicle vehicle = new PMVehicle(vehicleID, PMVState.Available, new GeographicPoint(41.3851f, 2.1734f));

        // Registrar el vehículo en el servidor mock
        mockServer.addVehicle(vehicleID, vehicle);

        // Configurar el QRDecoder para devolver el mismo VehicleID
        mockQRDecoder.setSimulatedVehicleID(vehicleID);

        // Escanear el QR para pasar el vehículo a estado NotAvailable
        handler.scanQR(mockImage);

        // Iniciar el desplazamiento
        handler.startDriving();

        // Verificar que el estado del vehículo ha cambiado a UnderWay
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
