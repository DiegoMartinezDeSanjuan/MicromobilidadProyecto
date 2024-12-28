package mocks;

import data.ServiceID;
import micromobility.JourneyService;

import java.math.BigDecimal;

/**
 * Implementación mock de la clase JourneyService.
 * Proporciona una simulación para pruebas unitarias.
 */
public class MockJourneyService extends JourneyService {

    private ServiceID serviceID;       // Identificador del servicio simulado
    private BigDecimal importValue;   // Importe del trayecto simulado

    /**
     * Constructor de MockJourneyService.
     *
     * @param serviceID   El identificador del servicio.
     * @param importValue El importe del trayecto.
     */
    public MockJourneyService(ServiceID serviceID, BigDecimal importValue) {
        super(null, null, null); // Llamada a super con valores nulos porque este es un mock
        this.serviceID = serviceID;
        this.importValue = importValue;
    }

    /**
     * Obtiene el identificador del servicio.
     *
     * @return El identificador del servicio.
     */
    @Override
    public ServiceID getServiceID() {
        return serviceID;
    }

    /**
     * Obtiene el importe del trayecto.
     *
     * @return El importe del trayecto.
     */
    @Override
    public BigDecimal getImportValue() {
        return importValue;
    }

    /**
     * Establece el importe del trayecto.
     *
     * @param importValue El importe a establecer.
     */
    @Override
    public void setImportValue(BigDecimal importValue) {
        this.importValue = importValue;
    }
}
