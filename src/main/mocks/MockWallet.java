package mocks;

import exceptions.NotEnoughWalletException;
import micromobility.payment.Wallet;

import java.math.BigDecimal;

public class MockWallet extends Wallet {

    public MockWallet(BigDecimal initialBalance) {
        super(initialBalance); // Llama al constructor de Wallet con el balance inicial
    }

    @Override
    public void deduct(BigDecimal amount) throws NotEnoughWalletException {
        // Simula la deducción del balance para las pruebas
        if (amount.compareTo(this.getBalance()) > 0) {
            throw new NotEnoughWalletException("Fondos insuficientes en el monedero.");
        }
        // Calcula el nuevo balance y actualiza
        BigDecimal newBalance = this.getBalance().subtract(amount);
        super.updateBalance(newBalance); // Usa un método de Wallet para actualizar el balance
    }
}

