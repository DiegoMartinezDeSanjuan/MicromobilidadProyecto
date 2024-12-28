package data;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Clase que representa un identificador de servicio junto con su monto asociado.
 */
public final class ServiceID {

    private final String id;
    private final BigDecimal amount;

    /**
     * Constructor que inicializa un identificador de servicio con un monto asociado.
     *
     * @param id     Identificador del servicio. No puede ser nulo o vacío.
     * @param amount Monto asociado al servicio. No puede ser nulo o negativo.
     * @throws IllegalArgumentException Si el ID es nulo/vacío o el monto es negativo.
     * @throws NullPointerException     Si el monto es nulo.
     */
    public ServiceID(String id, BigDecimal amount) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID del servicio no puede ser nulo o vacío.");
        }
        Objects.requireNonNull(amount, "El monto no puede ser nulo.");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo.");
        }
        this.id = id;
        this.amount = amount;
    }

    /**
     * Obtiene el identificador del servicio.
     *
     * @return ID del servicio.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el monto asociado al servicio.
     *
     * @return Monto del servicio.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "ServiceID {" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceID serviceID = (ServiceID) o;
        return id.equals(serviceID.id) && amount.equals(serviceID.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}
