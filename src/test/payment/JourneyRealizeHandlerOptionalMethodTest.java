package payment;

import data.GeographicPoint;
import micromobility.JourneyRealizeHandler;
import micromobility.payment.Wallet;
import micromobility.JourneyService;
import exceptions.InvalidPairingArgsException;
import exceptions.NotEnoughWalletException;
import exceptions.ProceduralException;
import exceptions.ConnectException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para los métodos opcionales de la clase JourneyRealizeHandler.
 */
public class JourneyRealizeHandlerOptionalMethodTest {

    @Test
    void testSelectPaymentMethodWalletSuccess() throws InvalidPairingArgsException, ConnectException, ProceduralException {
        // Configuración
        Wallet wallet = new Wallet(new BigDecimal("100.00"));
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        JourneyService journey = new JourneyService(point, LocalDate.now(), LocalTime.now());
        journey.setImportValue(new BigDecimal("50.00"));
        JourneyRealizeHandler handler = new JourneyRealizeHandler(null, null, null, null);

        handler.setWallet(wallet);
        handler.setCurrentJourney(journey);

        // Ejecución
        assertDoesNotThrow(() -> handler.selectPaymentMethod('W'), "El método de pago debería procesarse correctamente usando el monedero");

        // Verificación
        assertEquals(new BigDecimal("50.00"), wallet.getBalance(), "El saldo restante del monedero debería ser 50.00");
    }

    @Test
    void testSelectPaymentMethodInvalidOption() throws InvalidPairingArgsException {
        // Configuración
        JourneyRealizeHandler handler = new JourneyRealizeHandler(null, null, null, null);

        // Ejecución y Verificación
        assertThrows(ProceduralException.class, () -> handler.selectPaymentMethod('X'), "Debería lanzar ProceduralException para un método de pago inválido");
    }

    @Test
    void testSelectPaymentMethodInsufficientWalletFunds() throws InvalidPairingArgsException {
        // Configuración
        Wallet wallet = new Wallet(new BigDecimal("20.00"));
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        JourneyService journey = new JourneyService(point, LocalDate.now(), LocalTime.now());
        journey.setImportValue(new BigDecimal("50.00"));
        JourneyRealizeHandler handler = new JourneyRealizeHandler(null, null, null, null);

        handler.setWallet(wallet);
        handler.setCurrentJourney(journey);

        // Ejecución y Verificación
        assertThrows(NotEnoughWalletException.class, () -> handler.selectPaymentMethod('W'), "Debería lanzar NotEnoughWalletException cuando el saldo es insuficiente");
    }
}
