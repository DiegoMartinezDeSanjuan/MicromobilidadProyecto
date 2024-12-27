package payment;

import micromobility.JourneyRealizeHandler;
import micromobility.payment.Wallet;
import micromobility.JourneyService;
import exceptions.InvalidPairingArgsException;
import exceptions.NotEnoughWalletException;
import exceptions.ProceduralException;
import exceptions.ConnectException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para los métodos opcionales de la clase JourneyRealizeHandler.
 */
public class JourneyRealizeHandlerOptionalMethodTest {

    @Test
    void testSelectPaymentMethodWalletSuccess() throws InvalidPairingArgsException, ConnectException, ProceduralException {
        // Configuración
        Wallet wallet = new Wallet(new BigDecimal("100.00"));
        JourneyService journey = new JourneyService(null, null, null);
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
        JourneyService journey = new JourneyService(null, null, null);
        journey.setImportValue(new BigDecimal("50.00"));
        JourneyRealizeHandler handler = new JourneyRealizeHandler(null, null, null, null);

        handler.setWallet(wallet);
        handler.setCurrentJourney(journey);

        // Ejecución y Verificación
        assertThrows(NotEnoughWalletException.class, () -> handler.selectPaymentMethod('W'), "Debería lanzar NotEnoughWalletException cuando el saldo es insuficiente");
    }
}
