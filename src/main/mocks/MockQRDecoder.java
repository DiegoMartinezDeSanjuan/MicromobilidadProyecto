package mocks;

import data.VehicleID;
import exceptions.CorruptedImgException;
import exceptions.InvalidPairingArgsException;
import services.smartfeatures.QRDecoder;

import java.awt.image.BufferedImage;

public class MockQRDecoder implements QRDecoder {

    private boolean simulateCorruptedImage = false;
    private boolean simulateVehicleNotAvailable = false;

    public void setSimulateCorruptedImage(boolean simulateCorruptedImage) {
        this.simulateCorruptedImage = simulateCorruptedImage;
    }

    public void setSimulateVehicleNotAvailable(boolean simulateVehicleNotAvailable) {
        this.simulateVehicleNotAvailable = simulateVehicleNotAvailable;
    }

    @Override
    public VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException, InvalidPairingArgsException {
        if (simulateCorruptedImage) {
            throw new CorruptedImgException("Imagen corrupta.");
        }
        if (simulateVehicleNotAvailable) {
            // Devuelve un VehicleID válido para simular un vehículo no disponible
            return new VehicleID("V12345");
        }
        // Devuelve un VehicleID válido por defecto
        return new VehicleID("V67890");
    }
}
