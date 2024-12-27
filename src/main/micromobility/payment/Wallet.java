package micromobility.payment;

import exceptions.NotEnoughWalletException;
import java.math.BigDecimal;

public class Wallet {
    private BigDecimal balance;

    public Wallet(BigDecimal initialBalance) {
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser nulo o negativo.");
        }
        this.balance = initialBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deduct(BigDecimal imp) throws NotEnoughWalletException {
        if (imp == null || imp.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor a cero.");
        }

        if (balance.compareTo(imp) < 0) {
            throw new NotEnoughWalletException("Fondos insuficientes en el monedero.");
        }

        balance = balance.subtract(imp);
    }

    /**
     * Método protegido para actualizar el balance. Utilizado por mocks o subclases.
     *
     * @param newBalance El nuevo balance a establecer.
     */
    protected void updateBalance(BigDecimal newBalance) {
        if (newBalance == null || newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El nuevo balance no puede ser nulo o negativo.");
        }
        this.balance = newBalance;
    }
}
