package services.smartfeatures;

import data.VehicleID;
import exceptions.CorruptedImgException;
import exceptions.InvalidPairingArgsException;

import java.awt.image.BufferedImage;

/**
 * Interfaz para decodificar códigos QR de vehículos a partir de imágenes.
 */
public interface QRDecoder {

    /**
     * Decodifica un código QR a partir de la imagen proporcionada y devuelve el VehicleID correspondiente.
     *
     * @param qrImg La imagen que contiene el código QR a decodificar. No puede ser nula.
     * @return El VehicleID decodificado desde el código QR.
     * @throws CorruptedImgException       Si la imagen es inválida o no puede ser procesada.
     * @throws InvalidPairingArgsException Si el código QR contiene argumentos de emparejamiento inválidos.
     */
    VehicleID getVehicleID(BufferedImage qrImg) throws CorruptedImgException, InvalidPairingArgsException;
}
