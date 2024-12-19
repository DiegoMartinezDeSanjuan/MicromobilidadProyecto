package micromobility;

import data.GeographicPoint;
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
    private UserAccount user;
    private StationID endStation;

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

    // Resto de getters, setters y métodos (igual que antes)...


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
        this.user = user;
    }

    public StationID getEndStation() {
        return endStation;
    }

    public void setEndStation(StationID endStation) {
        this.endStation = endStation;
    }

}
