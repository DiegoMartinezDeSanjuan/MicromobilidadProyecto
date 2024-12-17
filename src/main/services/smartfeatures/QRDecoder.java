package services.smartfeatures;

import data.VehicleID;
import exceptions.CorruptedImgException;

import java.awt.image.BufferedImage;

/**
 * Interface for decoding vehicle QR codes from images.
 */
public interface QRDecoder {

    /**
     * Decodes a QR code from the provided image and returns the corresponding VehicleID.
     *
     * @param QRImg The image containing the QR code to be decoded.
     * @return The decoded VehicleID.
     * @throws CorruptedImgException If the image is invalid or cannot be processed.
     */
    VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException;
}
