package mocks;

import data.VehicleID;
import exceptions.CorruptedImgException;
import services.smartfeatures.QRDecoder;

import java.awt.image.BufferedImage;

/**
 * Mock para la implementación de la interfaz QRDecoder.
 */
public class MockQRDecoder implements QRDecoder {

    private VehicleID simulatedVehicleID; // Para devolver un VehicleID simulado
    private boolean simulateCorruptedImage; // Para simular un error de imagen corrupta

    /**
     * Configura el VehicleID que debe ser devuelto por el mock.
     *
     * @param vehicleID El VehicleID simulado.
     */
    public void setSimulatedVehicleID(VehicleID vehicleID) {
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

    @Override
    public VehicleID getVehicleID(BufferedImage qrImage) throws CorruptedImgException {
        if (simulateCorruptedImage) {
            throw new CorruptedImgException("La imagen del QR está corrupta.");
        }
        return simulatedVehicleID;
    }
}
