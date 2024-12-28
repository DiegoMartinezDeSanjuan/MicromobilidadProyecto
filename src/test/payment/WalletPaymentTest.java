package payment;

import data.GeographicPoint;
import micromobility.payment.Wallet;
import micromobility.payment.WalletPayment;
import micromobility.JourneyService;
import data.UserAccount;
import exceptions.InvalidPairingArgsException;
import exceptions.NotEnoughWalletException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase WalletPayment.
 */
public class WalletPaymentTest {

    /**
     * Verifica que el pago se procese exitosamente cuando el monedero tiene fondos suficientes.
     */
    @Test
    void testProcessPaymentSuccess() throws InvalidPairingArgsException {
        Wallet wallet = new Wallet(new BigDecimal("100.00"));
        UserAccount user = new UserAccount("testUser");
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        JourneyService journey = new JourneyService(point, LocalDate.now(), LocalTime.now());

        WalletPayment payment = new WalletPayment(journey, user, new BigDecimal("50.00"), wallet);

        // Ejecutar y verificar
        assertDoesNotThrow(payment::processPayment, "El pago debería procesarse sin errores");
        assertEquals(new BigDecimal("50.00"), wallet.getBalance(), "El saldo restante debería ser 50.00");
    }

    /**
     * Verifica que se lance una excepción cuando los fondos del monedero son insuficientes.
     */
    @Test
    void testProcessPaymentInsufficientFunds() throws InvalidPairingArgsException {
        Wallet wallet = new Wallet(new BigDecimal("30.00"));
        UserAccount user = new UserAccount("testUser");
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        JourneyService journey = new JourneyService(point, LocalDate.now(), LocalTime.now());

        WalletPayment payment = new WalletPayment(journey, user, new BigDecimal("50.00"), wallet);

        // Ejecutar y verificar
        assertThrows(NotEnoughWalletException.class, payment::processPayment, "Debería lanzar NotEnoughWalletException cuando el saldo es insuficiente");
    }

    /**
     * Verifica que se lance una excepción cuando el monedero es nulo.
     */
    @Test
    void testProcessPaymentNullWallet() throws InvalidPairingArgsException {
        Wallet wallet = null;
        UserAccount user = new UserAccount("testUser");
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        JourneyService journey = new JourneyService(point, LocalDate.now(), LocalTime.now());

        // Ejecutar y verificar
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(journey, user, new BigDecimal("50.00"), wallet), "Debería lanzar IllegalArgumentException cuando el monedero es nulo");
    }

    /**
     * Verifica que se lance una excepción cuando el importe es negativo.
     */
    @Test
    void testProcessPaymentInvalidAmount() throws InvalidPairingArgsException {
        Wallet wallet = new Wallet(new BigDecimal("100.00"));
        UserAccount user = new UserAccount("testUser");
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        JourneyService journey = new JourneyService(point, LocalDate.now(), LocalTime.now());

        // Ejecutar y verificar
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(journey, user, new BigDecimal("-50.00"), wallet), "Debería lanzar IllegalArgumentException para un importe negativo");
    }

    /**
     * Verifica que se lance una excepción cuando el importe es cero.
     */
    @Test
    void testProcessPaymentZeroAmount() throws InvalidPairingArgsException {
        Wallet wallet = new Wallet(new BigDecimal("100.00"));
        UserAccount user = new UserAccount("testUser");
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        JourneyService journey = new JourneyService(point, LocalDate.now(), LocalTime.now());

        // Ejecutar y verificar
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(journey, user, BigDecimal.ZERO, wallet), "Debería lanzar IllegalArgumentException para un importe de cero");
    }
}
