package data;

import java.math.BigDecimal;

public class ServiceID {
    private String id;
    private BigDecimal amount;

    public ServiceID(String id, BigDecimal amount) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID del servicio no puede ser nulo o vacío.");
        }
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
