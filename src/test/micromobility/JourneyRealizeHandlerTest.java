package micromobility;

import data.*;
import exceptions.*;
import mocks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase JourneyRealizeHandler.
 * Verifica el comportamiento de las funciones principales y la gestión de errores.
 */
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
        PMVehicle vehicle = createVehicle(vehicleID, PMVState.Available);

        mockQRDecoder.setSimulatedVehicleID(vehicleID);

        handler.scanQR(mockImage);

        assertEquals(PMVState.NotAvailable, vehicle.getState(), "El estado del vehículo debería ser NotAvailable.");
    }

    @Test
    void testScanQR_CorruptedImage() {
        mockQRDecoder.setSimulateCorruptedImage(true);
        BufferedImage mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        assertThrows(CorruptedImgException.class, () -> handler.scanQR(mockImage), "Debería lanzar una excepción por imagen corrupta.");
    }

    @Test
    void testBroadcastStationID_Success() throws InvalidPairingArgsException {
        StationID stationID = new StationID("S123");

        assertDoesNotThrow(() -> handler.broadcastStationID(stationID), "La transmisión del ID de la estación debería tener éxito.");
    }

    @Test
    void testStartDriving_Success() throws Exception {
        BufferedImage mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        VehicleID vehicleID = new VehicleID("V12345");
        PMVehicle vehicle = createVehicle(vehicleID, PMVState.Available);

        mockQRDecoder.setSimulatedVehicleID(vehicleID);
        handler.scanQR(mockImage);

        JourneyService journeyService = createJourneyService(new GeographicPoint(41.3851f, 2.1734f));
        handler.setCurrentJourney(journeyService);

        handler.startDriving();

        assertEquals(PMVState.UnderWay, vehicle.getState(), "El estado del vehículo debería ser UnderWay.");
        assertTrue(journeyService.isInProgress(), "El trayecto debería estar en progreso.");
    }

    @Test
    void testStopDriving_Success() throws Exception {
        VehicleID vehicleID = new VehicleID("V12345");
        PMVehicle vehicle = createVehicle(vehicleID, PMVState.UnderWay);

        JourneyService journeyService = createJourneyService(new GeographicPoint(41.3851f, 2.1734f));
        journeyService.setInProgress(true);

        handler.setCurrentJourney(journeyService);
        handler.setCurrentVehicle(vehicle);

        handler.stopDriving();

        assertEquals(PMVState.Available, vehicle.getState(), "El vehículo debería estar disponible después de detenerse.");
        assertFalse(journeyService.isInProgress(), "El trayecto debería haberse detenido.");
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

    // Métodos auxiliares

    private PMVehicle createVehicle(VehicleID vehicleID, PMVState state) throws InvalidPairingArgsException {
        PMVehicle vehicle = new PMVehicle(vehicleID, state, new GeographicPoint(41.3851f, 2.1734f));
        mockServer.addVehicle(vehicleID, vehicle);
        return vehicle;
    }

    private JourneyService createJourneyService(GeographicPoint originPoint) {
        LocalDate initDate = LocalDate.now().minusDays(1);
        LocalTime initHour = LocalTime.now().minusHours(2);
        return new JourneyService(originPoint, initDate, initHour);
    }
}
