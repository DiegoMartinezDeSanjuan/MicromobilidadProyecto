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

/**
 * Test para la clase JourneyService.
 * Verifica la inicialización, actualización de estado y cálculos relacionados con un trayecto.
 */
class JourneyServiceTest {

    private JourneyService journey;

    /**
     * Configuración inicial para cada test.
     *
     * @throws InvalidPairingArgsException Si los argumentos iniciales son inválidos.
     */
    @BeforeEach
    void setUp() throws InvalidPairingArgsException {
        journey = new JourneyService(
                new GeographicPoint(41.3851f, 2.1734f), // Origen: Barcelona
                LocalDate.of(2024, 12, 25),
                LocalTime.of(10, 0)
        );
    }

    /**
     * Verifica que el trayecto se inicializa correctamente con los datos proporcionados.
     */
    @Test
    void testJourneyInitialization() throws InvalidPairingArgsException {
        assertEquals(41.3851f, journey.getOriginPoint().getLatitude(), "La latitud del punto de origen no coincide.");
        assertEquals(2.1734f, journey.getOriginPoint().getLongitude(), "La longitud del punto de origen no coincide.");
        assertEquals(LocalDate.of(2024, 12, 25), journey.getStartDate(), "La fecha de inicio no coincide.");
        assertEquals(LocalTime.of(10, 0), journey.getStartTime(), "La hora de inicio no coincide.");
        assertTrue(journey.isInProgress(), "El estado inicial del trayecto debería ser 'en progreso'.");
    }

    /**
     * Verifica que el estado del trayecto se pueda actualizar correctamente.
     */
    @Test
    void testUpdateJourneyState() {
        journey.setInProgress(false);
        assertFalse(journey.isInProgress(), "El trayecto debería estar marcado como finalizado.");

        journey.setInProgress(true);
        assertTrue(journey.isInProgress(), "El trayecto debería estar marcado como en progreso.");
    }

    /**
     * Verifica los cálculos de distancia, duración y velocidad promedio.
     */
    @Test
    void testCalculateValues() throws InvalidPairingArgsException {
        // Configuración de valores simulados
        journey.setDistance(2.07f); // Distancia calculada
        journey.setDuration(30);   // Duración en minutos
        journey.setAverageSpeed((2.07f / 30) * 60); // Velocidad promedio en km/h

        // Validaciones
        assertEquals(30, journey.getDuration(), "La duración no coincide.");
        assertEquals(2.07f, journey.getDistance(), 0.1f, "La distancia no coincide.");
        assertEquals(4.14f, journey.getAverageSpeed(), 0.1f, "La velocidad promedio no coincide.");
    }

    /**
     * Verifica el cálculo del importe total del trayecto.
     */
    @Test
    void testCalculateImport() {
        journey.setDistance(2.07f); // Distancia simulada
        journey.setDuration(30);   // Duración simulada

        float tarifaPorKm = 0.5f;
        float tarifaPorMinuto = 0.1f;
        BigDecimal expectedImport = BigDecimal.valueOf(
                journey.getDistance() * tarifaPorKm + journey.getDuration() * tarifaPorMinuto
        );

        journey.setImportValue(expectedImport);

        assertEquals(expectedImport, journey.getImportValue(), "El importe calculado no coincide.");
    }

    /**
     * Verifica que valores inválidos para distancia o duración sean manejados correctamente.
     */
    @Test
    void testInvalidValuesForJourney() {
        assertThrows(IllegalArgumentException.class, () -> journey.setDistance(-1f), "La distancia negativa debería lanzar una excepción.");
        assertThrows(IllegalArgumentException.class, () -> journey.setDuration(-5), "La duración negativa debería lanzar una excepción.");
    }
}
