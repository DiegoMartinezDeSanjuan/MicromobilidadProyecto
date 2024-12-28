package micromobility;

import data.GeographicPoint;
import data.ServiceID;
import data.StationID;
import data.UserAccount;
import micromobility.payment.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Clase que representa un servicio de trayecto en el sistema de micromovilidad.
 */
public class JourneyService {

    private final LocalDate initDate;
    private final LocalTime initHour;
    private final GeographicPoint originPoint;
    private LocalDate endDate;
    private LocalTime endHour;
    private int duration;
    private float distance;
    private float avgSpeed;
    private GeographicPoint endPoint;
    private BigDecimal importValue;
    private boolean inProgress;
    private List<Payment> paymentMethods;
    private UserAccount user;
    private StationID endStation;
    private ServiceID serviceID;

    /**
     * Constructor de JourneyService.
     *
     * @param originPoint Punto de inicio del trayecto.
     * @param initDate    Fecha de inicio del trayecto.
     * @param initHour    Hora de inicio del trayecto.
     */
    public JourneyService(GeographicPoint originPoint, LocalDate initDate, LocalTime initHour) {
        if (originPoint == null || initDate == null || initHour == null) {
            throw new IllegalArgumentException("Los parámetros del constructor no pueden ser nulos.");
        }
        this.originPoint = originPoint;
        this.initDate = initDate;
        this.initHour = initHour;
        this.inProgress = true;
    }

    public LocalDate getStartDate() {
        return initDate;
    }

    public LocalTime getStartTime() {
        return initHour;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate == null || endDate.isBefore(initDate)) {
            throw new IllegalArgumentException("La fecha de finalización no puede ser nula ni anterior a la de inicio.");
        }
        this.endDate = endDate;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        if (endHour == null) {
            throw new IllegalArgumentException("La hora de finalización no puede ser nula.");
        }
        this.endHour = endHour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("La duración no puede ser negativa.");
        }
        this.duration = duration;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("La distancia no puede ser negativa.");
        }
        this.distance = distance;
    }

    public float getAverageSpeed() {
        return avgSpeed;
    }

    public void setAverageSpeed(float avgSpeed) {
        if (avgSpeed < 0) {
            throw new IllegalArgumentException("La velocidad promedio no puede ser negativa.");
        }
        this.avgSpeed = avgSpeed;
    }

    public GeographicPoint getOriginPoint() {
        return originPoint;
    }

    public GeographicPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(GeographicPoint endPoint) {
        if (endPoint == null) {
            throw new IllegalArgumentException("El punto de finalización no puede ser nulo.");
        }
        this.endPoint = endPoint;
    }

    public BigDecimal getImportValue() {
        return importValue;
    }

    public void setImportValue(BigDecimal importValue) {
        if (importValue == null || importValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El importe no puede ser nulo ni negativo.");
        }
        this.importValue = importValue;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        this.user = user;
    }

    public StationID getEndStation() {
        return endStation;
    }

    public void setEndStation(StationID endStation) {
        if (endStation == null) {
            throw new IllegalArgumentException("La estación final no puede ser nula.");
        }
        this.endStation = endStation;
    }

    public List<Payment> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<Payment> paymentMethods) {
        if (paymentMethods == null || paymentMethods.isEmpty()) {
            throw new IllegalArgumentException("Los métodos de pago no pueden ser nulos ni vacíos.");
        }
        this.paymentMethods = paymentMethods;
    }

    public ServiceID getServiceID() {
        return serviceID;
    }

    public void setServiceID(ServiceID serviceID) {
        if (serviceID == null) {
            throw new IllegalArgumentException("El ID de servicio no puede ser nulo.");
        }
        this.serviceID = serviceID;
    }
}
