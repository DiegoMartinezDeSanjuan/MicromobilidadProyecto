package services;

import data.*;
import exceptions.*;
import micromobility.JourneyService;
import micromobility.PMVehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Interfaz que define las operaciones del servidor para emparejamiento y gestión de vehículos.
 */
public interface Server {

    /**
     * Verifica si un PMV (Vehículo de Movilidad Personal) está disponible.
     *
     * @param vhID El identificador del vehículo a verificar.
     * @throws PMVNotAvailException Si el vehículo no está disponible.
     * @throws ConnectException     Si ocurre un problema de conexión con el servidor.
     */
    void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException;

    /**
     * Registra una operación de emparejamiento.
     *
     * @param user El usuario que inicia el emparejamiento.
     * @param veh  El vehículo que se empareja.
     * @param st   La estación donde ocurre el emparejamiento.
     * @param loc  La ubicación geográfica del emparejamiento.
     * @param date La fecha y hora del emparejamiento.
     * @throws InvalidPairingArgsException Si alguno de los argumentos es inválido.
     * @throws ConnectException            Si ocurre un problema de conexión con el servidor.
     */
    void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException;

    /**
     * Finaliza una operación de emparejamiento y registra información adicional.
     *
     * @param user El usuario que finaliza el emparejamiento.
     * @param veh  El vehículo que se desempareja.
     * @param st   La estación donde ocurre el desemparejamiento.
     * @param loc  La ubicación geográfica del desemparejamiento.
     * @param date La fecha y hora del desemparejamiento.
     * @param avSp La velocidad promedio durante el trayecto.
     * @param dist La distancia recorrida durante el trayecto.
     * @param dur  La duración del trayecto.
     * @param imp  El importe final a pagar por el trayecto.
     * @throws InvalidPairingArgsException Si alguno de los argumentos es inválido.
     * @throws ConnectException            Si ocurre un problema de conexión con el servidor.
     */
    void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date,
                     float avSp, float dist, int dur, BigDecimal imp)
            throws InvalidPairingArgsException, ConnectException;

    /**
     * Configura una operación de emparejamiento para un usuario y un vehículo.
     *
     * @param user El usuario que realiza el emparejamiento.
     * @param veh  El vehículo que se empareja.
     * @param st   La estación donde ocurre el emparejamiento.
     * @param loc  La ubicación geográfica del emparejamiento.
     * @param date La fecha y hora del emparejamiento.
     */
    void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date);

    /**
     * Desregistra un servicio relacionado con una operación de emparejamiento.
     *
     * @param service El servicio de trayecto a desregistrar.
     * @throws PairingNotFoundException Si no se encuentra el emparejamiento relacionado.
     */
    void unPairRegisterService(JourneyService service) throws PairingNotFoundException;

    /**
     * Registra la ubicación de un vehículo.
     *
     * @param veh El vehículo cuya ubicación se registra.
     * @param st  La estación donde se encuentra el vehículo.
     */
    void registerLocation(VehicleID veh, StationID st);

    /**
     * Registra un pago en el servidor.
     *
     * @param servID  El ID del servicio asociado al pago.
     * @param user    El usuario que realiza el pago.
     * @param imp     El importe del pago.
     * @param payMeth El método de pago (carácter que identifica el método).
     * @throws ConnectException Si ocurre un problema de conexión al registrar el pago.
     */
    void registerPayment(ServiceID servID, UserAccount user, BigDecimal imp, char payMeth) throws ConnectException;

    /**
     * Obtiene un vehículo basado en su identificador.
     *
     * @param vhID El identificador del vehículo.
     * @return La instancia de PMVehicle correspondiente.
     * @throws PMVNotAvailException Si el vehículo no se encuentra en el servidor.
     */
    PMVehicle getVehicleByID(VehicleID vhID) throws PMVNotAvailException;
}
