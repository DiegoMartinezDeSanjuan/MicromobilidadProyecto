package mocks;

import data.VehicleID;
import exceptions.CorruptedImgException;
import services.smartfeatures.QRDecoder;

import java.awt.image.BufferedImage;

/**
 * Implementación mock de la interfaz QRDecoder.
 * Simula el comportamiento del decodificador de códigos QR para pruebas.
 */
public class MockQRDecoder implements QRDecoder {

    private boolean simulateCorruptedImage = false; // Simula si la imagen del QR está corrupta

    @Override
    public VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException {
        if (simulateCorruptedImage) {
            throw new CorruptedImgException("La imagen del QR está corrupta o no puede ser procesada.");
        }
        if (QRImg == null) {
            throw new CorruptedImgException("La imagen del QR es nula.");
        }
        System.out.println("Mock: Código QR decodificado correctamente.");

        try {
            return new VehicleID("VALID_ID");
        } catch (Exception e) {
            throw new CorruptedImgException("Error al crear VehicleID: " + e.getMessage());
        }
    }

    /**
     * Configura el mock para simular una imagen corrupta.
     *
     * @param simulateCorrupted Indica si debe simular una imagen corrupta.
     */
    public void setSimulateCorruptedImage(boolean simulateCorrupted) {
        this.simulateCorruptedImage = simulateCorrupted;
    }
}
