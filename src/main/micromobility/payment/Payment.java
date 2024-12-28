package micromobility.payment;

import data.UserAccount;
import micromobility.JourneyService;

import java.math.BigDecimal;

/**
 * Clase abstracta que define el contrato para procesar pagos en el sistema.
 * Cada tipo de pago debe implementar el método `processPayment`.
 */
public abstract class Payment {

    protected JourneyService journeyService; // Servicio de trayecto asociado al pago
    protected UserAccount userAccount;       // Usuario que realiza el pago
    protected BigDecimal amount;            // Monto del pago

    /**
     * Constructor para inicializar los datos básicos del pago.
     *
     * @param journeyService El servicio de trayecto asociado al pago. No puede ser nulo.
     * @param userAccount    El usuario que realiza el pago. No puede ser nulo.
     * @param amount         El monto del pago. Debe ser mayor a 0.
     * @throws IllegalArgumentException Si alguno de los parámetros es inválido.
     */
    public Payment(JourneyService journeyService, UserAccount userAccount, BigDecimal amount) {
        validatePaymentData(journeyService, userAccount, amount);
        this.journeyService = journeyService;
        this.userAccount = userAccount;
        this.amount = amount;
    }

    /**
     * Método abstracto para procesar el pago.
     * Las subclases deben implementar este método con la lógica específica de cada tipo de pago.
     *
     * @throws Exception Si ocurre un error al procesar el pago.
     */
    public abstract void processPayment() throws Exception;

    /**
     * Obtiene el monto del pago.
     *
     * @return El monto del pago.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Obtiene el servicio de trayecto asociado al pago.
     *
     * @return El servicio de trayecto.
     */
    public JourneyService getJourneyService() {
        return journeyService;
    }

    /**
     * Obtiene el usuario que realiza el pago.
     *
     * @return El usuario que realiza el pago.
     */
    public UserAccount getUserAccount() {
        return userAccount;
    }

    /**
     * Valida los datos del pago.
     *
     * @param journeyService El servicio de trayecto asociado.
     * @param userAccount    El usuario que realiza el pago.
     * @param amount         El monto del pago.
     * @throws IllegalArgumentException Si alguno de los datos es inválido.
     */
    private void validatePaymentData(JourneyService journeyService, UserAccount userAccount, BigDecimal amount) {
        if (journeyService == null) {
            throw new IllegalArgumentException("El servicio de trayecto no puede ser nulo.");
        }
        if (userAccount == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0.");
        }
    }
}
