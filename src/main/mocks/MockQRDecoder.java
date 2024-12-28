package mocks;

import data.VehicleID;
import exceptions.CorruptedImgException;
import services.smartfeatures.QRDecoder;

import java.awt.image.BufferedImage;

/**
 * Mock para la implementación de la interfaz QRDecoder.
 * Simula el comportamiento de un decodificador de códigos QR para pruebas unitarias.
 */
public class MockQRDecoder implements QRDecoder {

    private VehicleID simulatedVehicleID; // VehicleID simulado a devolver
    private boolean simulateCorruptedImage; // Indicador para simular una imagen corrupta

    /**
     * Configura el VehicleID que debe ser devuelto por el mock.
     *
     * @param vehicleID El VehicleID simulado. No puede ser nulo.
     * @throws IllegalArgumentException Si el VehicleID es nulo.
     */
    public void setSimulatedVehicleID(VehicleID vehicleID) {
        if (vehicleID == null) {
            throw new IllegalArgumentException("El VehicleID no puede ser nulo.");
        }
        this.simulatedVehicleID = vehicleID;
    }

    /**
     * Configura si el mock debe simular una imagen corrupta.
     *
     * @param simulate true para simular una imagen corrupta, false de lo contrario.
     */
    public void setSimulateCorruptedImage(boolean simulate) {
        this.simulateCorruptedImage = simulate;
    }

    /**
     * Decodifica un código QR simulado.
     *
     * @param qrImage La imagen del QR a decodificar. No se utiliza en este mock.
     * @return El VehicleID simulado configurado previamente.
     * @throws CorruptedImgException Si se simula una imagen corrupta.
     */
    @Override
    public VehicleID getVehicleID(BufferedImage qrImage) throws CorruptedImgException {
        if (simulateCorruptedImage) {
            throw new CorruptedImgException("La imagen del QR está corrupta.");
        }
        return simulatedVehicleID;
    }
}
