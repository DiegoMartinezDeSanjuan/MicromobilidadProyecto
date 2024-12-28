package mocks;

import data.*;
import exceptions.*;
import micromobility.JourneyService;
import micromobility.PMVehicle;
import micromobility.PMVState;
import services.Server;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Mock para la implementación de la interfaz Server.
 * Simula el comportamiento del servidor para propósitos de pruebas.
 */
public class MockServer implements Server {

    private final Map<VehicleID, PMVehicle> vehicles = new HashMap<>();

    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException {
        PMVehicle vehicle = vehicles.get(vhID);
        if (vehicle == null) {
            throw new ConnectException("El vehículo no se encontró en el servidor.");
        }
        if (vehicle.getState() != PMVState.Available) {
            throw new PMVNotAvailException("El vehículo no está disponible.");
        }
        log("Mock: El vehículo " + vhID.getId() + " está disponible.");
    }

    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException {
        validarArgumentosEmparejamiento(user, veh, st, loc, date);
        log("Mock: Emparejamiento registrado correctamente para el usuario " + user.getUsername() +
                " y el vehículo " + veh.getId() + ".");
    }

    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date,
                            float avSp, float dist, int dur, BigDecimal imp)
            throws InvalidPairingArgsException, ConnectException {
        validarTiempoFinalizacion(date);
        validarValoresTrayecto(dist, dur, imp);
        log("Mock: Emparejamiento finalizado correctamente.");
    }

    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {
        log("Mock: Emparejamiento configurado para el usuario " + user.getUsername() +
                " y el vehículo " + veh.getId() + ".");
    }

    @Override
    public void unPairRegisterService(JourneyService service) throws PairingNotFoundException {
        if (service == null) {
            throw new PairingNotFoundException("El servicio no se encontró.");
        }
        log("Mock: Servicio desvinculado correctamente.");
    }

    @Override
    public void registerLocation(VehicleID veh, StationID st) {
        if (veh == null || st == null) {
            throw new IllegalArgumentException("El vehículo o la estación son inválidos.");
        }
        log("Mock: Ubicación del vehículo " + veh.getId() + " registrada en la estación " + st.getId() + ".");
    }

    @Override
    public PMVehicle getVehicleByID(VehicleID vhID) throws PMVNotAvailException {
        PMVehicle vehicle = vehicles.get(vhID);
        if (vehicle == null) {
            throw new PMVNotAvailException("El vehículo no se encontró en el servidor.");
        }
        return vehicle;
    }

    /**
     * Simula agregar un vehículo al mock del servidor.
     *
     * @param vhID    El ID del vehículo.
     * @param vehicle La instancia de PMVehicle.
     */
    public void addVehicle(VehicleID vhID, PMVehicle vehicle) {
        if (vhID == null || vehicle == null) {
            throw new IllegalArgumentException("VehicleID o PMVehicle no pueden ser nulos.");
        }
        vehicles.put(vhID, vehicle);
        log("Mock: Vehículo " + vhID.getId() + " añadido correctamente.");
    }

    @Override
    public void registerPayment(ServiceID servID, UserAccount user, BigDecimal imp, char payMeth) throws ConnectException {
        validarArgumentosPago(servID, user, imp, payMeth);
        log("Registro del pago:");
        log("ServiceID: " + servID.getId());
        log("Usuario: " + user.getUsername());
        log("Importe: " + imp);
        log("Método de pago: " + payMeth);
    }

    // Métodos privados de validación
    private void validarArgumentosEmparejamiento(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {
        if (user == null || veh == null || st == null || loc == null || date == null) {
            throw new IllegalArgumentException("Argumentos inválidos para el emparejamiento.");
        }
    }

    private void validarTiempoFinalizacion(LocalDateTime date) {
        if (date == null || date.isBefore(LocalDateTime.now().minusYears(1))) {
            throw new IllegalArgumentException("Tiempo de finalización inválido.");
        }
    }

    private void validarValoresTrayecto(float dist, int dur, BigDecimal imp) {
        if (dist <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor a 0.");
        }
        if (dur <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0.");
        }
        if (imp == null || imp.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor a 0.");
        }
    }

    private void validarArgumentosPago(ServiceID servID, UserAccount user, BigDecimal imp, char payMeth) {
        if (servID == null || user == null || imp == null) {
            throw new IllegalArgumentException("El ServiceID, UserAccount o el importe no pueden ser nulos.");
        }
        if (imp.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor que 0.");
        }
        if (!isValidPayMethod(payMeth)) {
            throw new IllegalArgumentException("Método de pago inválido.");
        }
    }

    private boolean isValidPayMethod(char payMeth) {
        return payMeth == 'C' || payMeth == 'D' || payMeth == 'P' || payMeth == 'W';
    }

    // Método privado para manejar logs
    private void log(String message) {
        System.out.println(message);
    }
}
