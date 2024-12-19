package micromobility;

import data.GeographicPoint;
import exceptions.InvalidPairingArgsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class JourneyServiceTest {

    private JourneyService journey;

    @BeforeEach
    void setUp() throws InvalidPairingArgsException {
        journey = new JourneyService(
                new GeographicPoint(41.3851f, 2.1734f), // Origen: Barcelona
                LocalDate.of(2024, 12, 25),
                LocalTime.of(10, 0)
        );
    }

    @Test
    void testJourneyInitialization() throws InvalidPairingArgsException {
        assertEquals(41.3851f, journey.getOriginPoint().getLatitude());
        assertEquals(2.1734f, journey.getOriginPoint().getLongitude());
        assertEquals(LocalDate.of(2024, 12, 25), journey.getStartDate());
        assertEquals(LocalTime.of(10, 0), journey.getStartTime());
        assertTrue(journey.isInProgress());
    }

    @Test
    void testUpdateJourneyState() {
        journey.setInProgress(false);
        assertFalse(journey.isInProgress());

        journey.setInProgress(true);
        assertTrue(journey.isInProgress());
    }

    @Test
    void testCalculateValues() throws InvalidPairingArgsException {
        GeographicPoint endPoint = new GeographicPoint(41.4036f, 2.1744f); // Sagrada Familia
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 25, 10, 30);

        journey.setDistance(2.07f); // Distancia calculada por Haversine
        journey.setDuration(30);   // Diferencia de tiempo en minutos
        journey.setAverageSpeed((2.07f / 30) * 60);

        assertEquals(30, journey.getDuration());
        assertEquals(2.07f, journey.getDistance(), 0.1f);
        assertEquals(4.14f, journey.getAverageSpeed(), 0.1f);
    }

    @Test
    void testCalculateImport() {
        journey.setDistance(2.07f); // Ejemplo de distancia
        journey.setDuration(30);   // Ejemplo de duración

        float tarifaPorKm = 0.5f;
        float tarifaPorMinuto = 0.1f;
        BigDecimal expectedImport = BigDecimal.valueOf(
                journey.getDistance() * tarifaPorKm + journey.getDuration() * tarifaPorMinuto
        );

        journey.setImportValue(expectedImport);

        assertEquals(expectedImport, journey.getImportValue());
    }
}
