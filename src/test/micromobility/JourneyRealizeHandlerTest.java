package micromobility;

import data.*;
import exceptions.*;
import mocks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

        // Crear un JourneyService válido
        GeographicPoint originPoint = new GeographicPoint(41.3851f, 2.1734f);
        LocalDate initDate = LocalDate.now();
        LocalTime initHour = LocalTime.now();
        JourneyService journeyService = new JourneyService(originPoint, initDate, initHour);

        // Configurar el handler con el JourneyService
        handler.setCurrentJourney(journeyService);

        // Iniciar el desplazamiento
        handler.startDriving();

        // Verificar que el estado del vehículo ha cambiado a UnderWay
        assertEquals(PMVState.UnderWay, vehicle.getState());

        // Verificar que el JourneyService está en progreso
        assertTrue(journeyService.isInProgress());
    }

    @Test
    void testStartDriving_NoVehicle() {
        assertThrows(ProceduralException.class, () -> handler.startDriving());
    }

    @Test
    void testStopDriving_Success() throws Exception {
        // Crear una imagen simulada para el QR
        BufferedImage mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        // Crear un VehicleID y un vehículo en estado UnderWay
        VehicleID vehicleID = new VehicleID("V12345");
        PMVehicle vehicle = new PMVehicle(vehicleID, PMVState.UnderWay, new GeographicPoint(41.3851f, 2.1734f));

        // Configurar el servidor mock para manejar el vehículo
        mockServer.addVehicle(vehicleID, vehicle);

        // Crear un JourneyService simulado y asociarlo al controlador
        JourneyService journeyService = new JourneyService(
                new GeographicPoint(41.3851f, 2.1734f), // Punto de inicio
                LocalDate.now(),                        // Fecha de inicio
                LocalTime.now()                         // Hora de inicio
        );
        journeyService.setInProgress(true); // Asegurar que el trayecto está en progreso
        handler.setCurrentJourney(journeyService);

        // Asociar el vehículo al controlador directamente sin escanear el QR
        handler.setCurrentVehicle(vehicle);

        // Detener el desplazamiento
        handler.stopDriving();

        // Validar que el trayecto ha finalizado correctamente
        assertFalse(handler.getCurrentJourney().isInProgress());
        assertEquals(PMVState.Available, vehicle.getState());
        System.out.println("Test stopDriving ejecutado correctamente.");
    }





    @Test
    void testStopDriving_NoVehicle() {
        assertThrows(ProceduralException.class, () -> handler.stopDriving());
    }

    @Test
    void testUnPairVehicle_Success() throws Exception {
        // Configurar el vehículo con una ubicación inicial
        VehicleID vehicleID = new VehicleID("V12345");
        GeographicPoint vehicleLocation = new GeographicPoint(41.4020f, 2.1910f); // Ubicación actual válida
        PMVehicle vehicle = new PMVehicle(vehicleID, PMVState.NotAvailable, vehicleLocation);
        mockServer.addVehicle(vehicleID, vehicle);

        // Configurar la estación de inicio
        GeographicPoint originPoint = new GeographicPoint(41.3851f, 2.1734f);
        StationID stationID = new StationID("ST123");

        // Configurar una fecha y hora de inicio más antigua
        LocalDate journeyStartDate = LocalDate.now().minusDays(1);
        LocalTime journeyStartTime = LocalTime.now().minusHours(2);

        // Configurar el JourneyService y asociarlo al handler
        JourneyService journeyService = new JourneyService(originPoint, journeyStartDate, journeyStartTime);
        journeyService.setInProgress(true);
        journeyService.setEndStation(stationID);

        // Asociar JourneyService y vehículo al handler
        handler.setCurrentJourney(journeyService);
        handler.setCurrentVehicle(vehicle);

        // Finalizar el trayecto
        handler.unPairVehicle();

        // Verificar los resultados
        assertEquals(PMVState.Available, vehicle.getState());
        assertFalse(journeyService.isInProgress());
        assertTrue(journeyService.getDistance() > 0);
        assertTrue(journeyService.getDuration() > 0);
        assertTrue(journeyService.getAverageSpeed() > 0);
        assertTrue(journeyService.getImportValue().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testUnPairVehicle_NoJourney() {
        assertThrows(ProceduralException.class, () -> handler.unPairVehicle());
    }
}
