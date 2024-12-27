package payment;

import micromobility.payment.Wallet;
import exceptions.NotEnoughWalletException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase Wallet.
 */
public class WalletTest {

    @Test
    void testDeductSuccessful() {
        // Configuración
        Wallet wallet = new Wallet(new BigDecimal("100.00"));

        // Ejecución
        wallet.deduct(new BigDecimal("50.00"));

        // Verificación
        assertEquals(new BigDecimal("50.00"), wallet.getBalance(), "El saldo restante debería ser 50.00");
    }

    @Test
    void testDeductInsufficientFunds() {
        // Configuración
        Wallet wallet = new Wallet(new BigDecimal("30.00"));

        // Ejecución y Verificación
        assertThrows(NotEnoughWalletException.class, () -> wallet.deduct(new BigDecimal("50.00")), "Debería lanzar NotEnoughWalletException cuando el saldo es insuficiente");
    }

    @Test
    void testDeductInvalidAmountNegative() {
        // Configuración
        Wallet wallet = new Wallet(new BigDecimal("50.00"));

        // Ejecución y Verificación
        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(new BigDecimal("-10.00")), "Debería lanzar IllegalArgumentException para un importe negativo");
    }

    @Test
    void testDeductInvalidAmountZero() {
        // Configuración
        Wallet wallet = new Wallet(new BigDecimal("50.00"));

        // Ejecución y Verificación
        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(new BigDecimal("0.00")), "Debería lanzar IllegalArgumentException para un importe de cero");
    }

    @Test
    void testInitialBalanceValidation() {
        // Ejecución y Verificación
        assertThrows(IllegalArgumentException.class, () -> new Wallet(null), "Debería lanzar IllegalArgumentException para un saldo inicial nulo");
        assertThrows(IllegalArgumentException.class, () -> new Wallet(new BigDecimal("-1.00")), "Debería lanzar IllegalArgumentException para un saldo inicial negativo");
    }

    @Test
    void testGetBalance() {
        // Configuración
        Wallet wallet = new Wallet(new BigDecimal("75.00"));

        // Ejecución
        BigDecimal balance = wallet.getBalance();

        // Verificación
        assertEquals(new BigDecimal("75.00"), balance, "El saldo inicial debería coincidir con el valor proporcionado");
    }
}
