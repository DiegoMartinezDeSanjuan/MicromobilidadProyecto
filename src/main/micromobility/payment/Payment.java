package micromobility.payment;

import data.UserAccount;
import micromobility.JourneyService;
import java.math.BigDecimal;

public abstract class Payment {
    protected JourneyService journeyService;
    protected UserAccount userAccount;
    protected BigDecimal amount;

    public Payment(JourneyService journeyService, UserAccount userAccount, BigDecimal amount) {
        if (journeyService == null || userAccount == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Datos de pago inválidos.");
        }

        this.journeyService = journeyService;
        this.userAccount = userAccount;
        this.amount = amount;
    }

    public abstract void processPayment() throws Exception;

    public BigDecimal getAmount() {
        return amount;
    }

    public JourneyService getJourneyService() {
        return journeyService;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
}
