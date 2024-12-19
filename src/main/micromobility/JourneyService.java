package micromobility;

import data.GeographicPoint;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import micromobility.payment.Payment;

/**
 * Clase que representa un servicio de trayecto en el sistema de micromovilidad.
 */
public class JourneyService {

    // Atributos según el modelo del dominio
    private LocalDate initDate;       // Fecha de inicio del trayecto
    private LocalTime initHour;       // Hora de inicio del trayecto
    private LocalDate endDate;        // Fecha de finalización del trayecto
    private LocalTime endHour;        // Hora de finalización del trayecto
    private float duration;           // Duración del trayecto en minutos
    private float distance;           // Distancia recorrida en kilómetros
    private float avgSpeed;           // Velocidad promedio en km/h
    private GeographicPoint originPoint;  // Punto de origen del trayecto
    private GeographicPoint endPoint;     // Punto de destino del trayecto
    private BigDecimal importValue;       // Importe total del trayecto
    private boolean inProgress;           // Indica si el trayecto está en curso
    private List<Payment> paymentMethods; // Métodos de pago asociados

    /**
     * Constructor de JourneyService.
     *
     * @param originPoint Punto de origen del trayecto.
     * @param initDate Fecha de inicio.
     * @param initHour Hora de inicio.
     */
    public JourneyService(GeographicPoint originPoint, LocalDate initDate, LocalTime initHour) {
        this.originPoint = originPoint;
        this.initDate = initDate;
        this.initHour = initHour;
        this.inProgress = true; // El trayecto comienza en curso
    }

    // Getters

    public LocalDate getInitDate() {
        return initDate;
    }

    public LocalTime getInitHour() {
        return initHour;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public float getDuration() {
        return duration;
    }

    public float getDistance() {
        return distance;
    }

    public float getAvgSpeed() {
        return avgSpeed;
    }

    public GeographicPoint getOriginPoint() {
        return originPoint;
    }

    public GeographicPoint getEndPoint() {
        return endPoint;
    }

    public BigDecimal getImportValue() {
        return importValue;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public List<Payment> getPaymentMethods() {
        return paymentMethods;
    }

    // Setters solicitados

    /**
     * Configura el inicio del servicio, indicando que está en progreso.
     */
    public void setServiceInit() {
        this.inProgress = true;
        this.initDate = LocalDate.now();
        this.initHour = LocalTime.now();
    }

    /**
     * Finaliza el servicio, configurando los datos de finalización.
     *
     * @param endPoint Punto de destino.
     * @param distance Distancia recorrida en kilómetros.
     * @param duration Duración del trayecto en minutos.
     * @param avgSpeed Velocidad promedio en km/h.
     * @param importValue Importe total del trayecto.
     */
    public void setServiceFinish(GeographicPoint endPoint, float distance, float duration, float avgSpeed, BigDecimal importValue) {
        this.endPoint = endPoint;
        this.endDate = LocalDate.now();
        this.endHour = LocalTime.now();
        this.distance = distance;
        this.duration = duration;
        this.avgSpeed = avgSpeed;
        this.importValue = importValue;
        this.inProgress = false;
    }

    /**
     * Asocia métodos de pago al servicio.
     *
     * @param paymentMethods Lista de métodos de pago.
     */
    public void setPaymentMethods(List<Payment> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
