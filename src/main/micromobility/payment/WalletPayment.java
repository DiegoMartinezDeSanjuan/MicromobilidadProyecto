package micromobility.payment;

import data.UserAccount;
import exceptions.NotEnoughWalletException;
import micromobility.JourneyService;
import micromobility.payment.Wallet;

import java.math.BigDecimal;

public class WalletPayment extends Payment {
    private Wallet wallet;

    public WalletPayment(JourneyService journeyService, UserAccount userAccount, BigDecimal amount, Wallet wallet) {
        super(journeyService, userAccount, amount);
        if (wallet == null) {
            throw new IllegalArgumentException("El monedero no puede ser nulo.");
        }
        this.wallet = wallet;
    }

    @Override
    public void processPayment() throws NotEnoughWalletException {
        wallet.deduct(amount);
        System.out.println("Pago realizado con éxito desde el monedero.");
    }

    public Wallet getWallet() {
        return wallet;
    }
}
