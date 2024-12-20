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
            throw new InvalidPairingArgsException("VehicleID no válido en el QR.");
        }
        return new VehicleID("V12345");
    }
}
