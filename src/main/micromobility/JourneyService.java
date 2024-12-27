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

public class JourneyService {

    private LocalDate initDate;
    private LocalTime initHour;
    private LocalDate endDate;
    private LocalTime endHour;
    private int duration;
    private float distance;
    private float avgSpeed;
    private GeographicPoint originPoint;
    private GeographicPoint endPoint;
    private BigDecimal importValue;
    private boolean inProgress;
    private List<Payment> paymentMethods;
    private UserAccount user;
    private StationID endStation;
    private ServiceID serviceID;


    public JourneyService(GeographicPoint originPoint, LocalDate initDate, LocalTime initHour) {
        this.originPoint = originPoint;
        this.initDate = initDate;
        this.initHour = initHour;
        this.serviceID = serviceID;
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
        this.endDate = endDate;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getAverageSpeed() {
        return avgSpeed;
    }

    public void setAverageSpeed(float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public GeographicPoint getOriginPoint() {
        return originPoint;
    }

    public void setOriginPoint(GeographicPoint originPoint) {
        this.originPoint = originPoint;
    }

    public GeographicPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(GeographicPoint endPoint) {
        this.endPoint = endPoint;
    }

    public BigDecimal getImportValue() {
        return importValue;
    }

    public void setImportValue(BigDecimal importValue) {
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
        this.user = user;
    }

    public StationID getEndStation() {
        return endStation;
    }

    public void setEndStation(StationID endStation) {
        this.endStation = endStation;
    }

    public List<Payment> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<Payment> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public ServiceID getServiceID() {
        return serviceID;
    }
}
