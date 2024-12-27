package mocks;

import data.ServiceID;
import micromobility.JourneyService;

import java.math.BigDecimal;

public class MockJourneyService extends JourneyService {

    private ServiceID serviceID;
    private BigDecimal importValue;

    public MockJourneyService(ServiceID serviceID, BigDecimal importValue) {
        super(null, null, null); // Llamada a super con valores nulos porque este es un mock
        this.serviceID = serviceID;
        this.importValue = importValue;
    }

    @Override
    public ServiceID getServiceID() {
        return serviceID;
    }

    @Override
    public BigDecimal getImportValue() {
        return importValue;
    }

    @Override
    public void setImportValue(BigDecimal importValue) {
        this.importValue = importValue;
    }
}