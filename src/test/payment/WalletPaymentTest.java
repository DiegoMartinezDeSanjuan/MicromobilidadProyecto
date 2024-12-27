package payment;

import micromobility.payment.Wallet;
import micromobility.payment.WalletPayment;
import micromobility.JourneyService;
import data.UserAccount;
import exceptions.InvalidPairingArgsException;
import exceptions.NotEnoughWalletException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase WalletPayment.
 */
public class WalletPaymentTest {

    @Test
    void testProcessPaymentSuccess() throws InvalidPairingArgsException {
        // Configuración
        Wallet wallet = new Wallet(new BigDecimal("100.00"));
        UserAccount user = new UserAccount("testUser");
        JourneyService journey = new JourneyService(null, null, null);

        WalletPayment payment = new WalletPayment(journey, user, new BigDecimal("50.00"), wallet);

        // Ejecución
        assertDoesNotThrow(payment::processPayment, "El pago debería procesarse sin errores");

        // Verificación
        assertEquals(new BigDecimal("50.00"), wallet.getBalance(), "El saldo restante debería ser 50.00");
    }

    @Test
    void testProcessPaymentInsufficientFunds() throws InvalidPairingArgsException {
        // Configuración
        Wallet wallet = new Wallet(new BigDecimal("30.00"));
        UserAccount user = new UserAccount("testUser");
        JourneyService journey = new JourneyService(null, null, null);

        WalletPayment payment = new WalletPayment(journey, user, new BigDecimal("50.00"), wallet);

        // Ejecución y Verificación
        assertThrows(NotEnoughWalletException.class, payment::processPayment, "Debería lanzar NotEnoughWalletException cuando el saldo es insuficiente");
    }

    @Test
    void testProcessPaymentNullWallet() throws InvalidPairingArgsException {
        // Configuración
        Wallet wallet = null;
        UserAccount user = new UserAccount("testUser");
        JourneyService journey = new JourneyService(null, null, null);

        // Ejecución y Verificación
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(journey, user, new BigDecimal("50.00"), wallet), "Debería lanzar IllegalArgumentException cuando el monedero es nulo");
    }

    @Test
    void testProcessPaymentInvalidAmount() throws InvalidPairingArgsException {
        // Configuración
        Wallet wallet = new Wallet(new BigDecimal("100.00"));
        UserAccount user = new UserAccount("testUser");
        JourneyService journey = new JourneyService(null, null, null);

        // Ejecución y Verificación
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(journey, user, new BigDecimal("-50.00"), wallet), "Debería lanzar IllegalArgumentException para un importe negativo");
    }
}
