package mocks;

import exceptions.NotEnoughWalletException;
import micromobility.payment.Wallet;

import java.math.BigDecimal;

/**
 * Mock para la clase Wallet.
 * Simula el comportamiento de un monedero para pruebas unitarias.
 */
public class MockWallet extends Wallet {

    /**
     * Constructor del MockWallet.
     *
     * @param initialBalance El saldo inicial del monedero simulado.
     * @throws IllegalArgumentException Si el saldo inicial es nulo o negativo.
     */
    public MockWallet(BigDecimal initialBalance) {
        super(initialBalance); // Llama al constructor de Wallet con el balance inicial
    }

    /**
     * Simula la deducción de saldo del monedero.
     *
     * @param amount El monto a deducir.
     * @throws NotEnoughWalletException Si no hay fondos suficientes en el monedero.
     * @throws IllegalArgumentException Si el monto a deducir es nulo o negativo.
     */
    @Override
    public void deduct(BigDecimal amount) throws NotEnoughWalletException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto a deducir debe ser mayor que cero.");
        }
        if (amount.compareTo(this.getBalance()) > 0) {
            throw new NotEnoughWalletException("Fondos insuficientes en el monedero.");
        }
        // Calcula el nuevo balance y lo actualiza utilizando el método de la clase base
        BigDecimal newBalance = this.getBalance().subtract(amount);
        super.updateBalance(newBalance); // Método protegido de Wallet
    }
}
