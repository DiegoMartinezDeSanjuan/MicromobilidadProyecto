package services.smartfeatures;

import exceptions.ConnectException;
import exceptions.PMVNotAvailException;
import exceptions.ProceduralException;

/**
 * Interfaz que representa el software del microcontrolador Arduino para vehículos.
 * Maneja acciones como iniciar, detener y gestionar la conexión Bluetooth.
 */
public interface ArduinoMicroController {

    /**
     * Establece la conexión Bluetooth entre el vehículo y el smartphone.
     *
     * @throws ConnectException Si falla la conexión Bluetooth.
     */
    void setBTconnection() throws ConnectException;

    /**
     * Inicia el desplazamiento del vehículo.
     *
     * @throws PMVNotAvailException Si hay un problema físico que impide que el vehículo arranque.
     * @throws ConnectException Si ocurre un fallo en la conexión Bluetooth.
     * @throws ProceduralException Si ocurre un problema inesperado en el proceso.
     */
    void startDriving() throws PMVNotAvailException, ConnectException, ProceduralException;

    /**
     * Detiene el desplazamiento del vehículo, detectando frenadas continuas.
     *
     * @throws PMVNotAvailException Si hay un problema físico con el sistema de frenos.
     * @throws ConnectException Si ocurre un fallo en la conexión Bluetooth.
     * @throws ProceduralException Si ocurre un problema inesperado en el proceso.
     */
    void stopDriving() throws PMVNotAvailException, ConnectException, ProceduralException;

    /**
     * Finaliza la conexión Bluetooth entre el vehículo y el smartphone.
     * Este método debe usarse al finalizar el trayecto o desconectar el sistema.
     */
    void undoBTconnection();
}
