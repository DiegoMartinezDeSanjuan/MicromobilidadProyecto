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

    /**
     * Verifica que se deduzca correctamente un monto cuando hay fondos suficientes.
     */
    @Test
    void testDeductSuccessful() {
        Wallet wallet = new Wallet(new BigDecimal("100.00"));

        wallet.deduct(new BigDecimal("50.00"));

        assertEquals(new BigDecimal("50.00"), wallet.getBalance(), "El saldo restante debería ser 50.00");
    }

    /**
     * Verifica que se lance una excepción cuando no hay fondos suficientes.
     */
    @Test
    void testDeductInsufficientFunds() {
        Wallet wallet = new Wallet(new BigDecimal("30.00"));

        assertThrows(NotEnoughWalletException.class, () -> wallet.deduct(new BigDecimal("50.00")), "Debería lanzar NotEnoughWalletException cuando el saldo es insuficiente");
    }

    /**
     * Verifica que se lance una excepción para un importe negativo.
     */
    @Test
    void testDeductInvalidAmountNegative() {
        Wallet wallet = new Wallet(new BigDecimal("50.00"));

        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(new BigDecimal("-10.00")), "Debería lanzar IllegalArgumentException para un importe negativo");
    }

    /**
     * Verifica que se lance una excepción para un importe de cero.
     */
    @Test
    void testDeductInvalidAmountZero() {
        Wallet wallet = new Wallet(new BigDecimal("50.00"));

        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(new BigDecimal("0.00")), "Debería lanzar IllegalArgumentException para un importe de cero");
    }

    /**
     * Verifica que se validen correctamente los valores iniciales del saldo.
     */
    @Test
    void testInitialBalanceValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Wallet(null), "Debería lanzar IllegalArgumentException para un saldo inicial nulo");
        assertThrows(IllegalArgumentException.class, () -> new Wallet(new BigDecimal("-1.00")), "Debería lanzar IllegalArgumentException para un saldo inicial negativo");
    }

    /**
     * Verifica que el saldo inicial se devuelva correctamente.
     */
    @Test
    void testGetBalance() {
        Wallet wallet = new Wallet(new BigDecimal("75.00"));

        BigDecimal balance = wallet.getBalance();

        assertEquals(new BigDecimal("75.00"), balance, "El saldo inicial debería coincidir con el valor proporcionado");
    }
}
