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
        System.out.println("Mock: El vehículo " + vhID.getId() + " está disponible.");
    }

    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException {
        if (user == null || veh == null || st == null || loc == null || date == null) {
            throw new InvalidPairingArgsException("Argumentos inválidos para el emparejamiento.");
        }
        System.out.println("Mock: Emparejamiento registrado correctamente para el usuario " + user.getUsername() +
                " y el vehículo " + veh.getId() + ".");
    }

    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date,
                            float avSp, float dist, int dur, BigDecimal imp)
            throws InvalidPairingArgsException, ConnectException {

        // Validaciones mínimas necesarias que no estén cubiertas en otro lugar
        if (date == null || date.isBefore(LocalDateTime.now().minusYears(1))) {
            throw new InvalidPairingArgsException("Tiempo de finalización inválido.");
        }

        if (dur <= 0) {
            throw new InvalidPairingArgsException("La duración debe ser mayor a 0.");
        }

        if (dist <= 0) {
            throw new InvalidPairingArgsException("La distancia debe ser mayor a 0.");
        }

        if (imp == null || imp.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPairingArgsException("El importe debe ser mayor a 0.");
        }

        System.out.println("Mock: Emparejamiento finalizado correctamente.");
    }


    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {
        System.out.println("Mock: Emparejamiento configurado para el usuario " + user.getUsername() +
                " y el vehículo " + veh.getId() + ".");
    }

    @Override
    public void unPairRegisterService(JourneyService service) throws PairingNotFoundException {
        if (service == null) {
            throw new PairingNotFoundException("El servicio no se encontró.");
        }
        System.out.println("Mock: Servicio desvinculado correctamente.");
    }

    @Override
    public void registerLocation(VehicleID veh, StationID st) {
        if (veh == null || st == null) {
            throw new IllegalArgumentException("El vehículo o la estación son inválidos.");
        }
        System.out.println("Mock: Ubicación del vehículo " + veh.getId() + " registrada en la estación " + st.getId() + ".");
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
        System.out.println("Mock: Vehículo " + vhID.getId() + " añadido correctamente.");
    }

    @Override
    public void registerPayment(ServiceID servID, UserAccount user, BigDecimal imp, char payMeth) throws ConnectException {
        if (servID == null || user == null || imp == null) {
            throw new IllegalArgumentException("El ServiceID, UserAccount o el importe no pueden ser nulos.");
        }
        if (imp.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor que 0.");
        }
        if (!isValidPayMethod(payMeth)) {
            throw new IllegalArgumentException("Método de pago inválido.");
        }

        // Simulación de registro en el servidor
        System.out.println("Registro del pago:");
        System.out.println("ServiceID: " + servID.getId());
        System.out.println("Usuario: " + user.getUsername());
        System.out.println("Importe: " + imp);
        System.out.println("Método de pago: " + payMeth);
    }

    /**
     * Valida si el método de pago es válido.
     *
     * @param payMeth Método de pago a validar.
     * @return True si el método de pago es válido, False en caso contrario.
     */
    private boolean isValidPayMethod(char payMeth) {
        return payMeth == 'C' || payMeth == 'D' || payMeth == 'P' || payMeth == 'W';
    }

}
