package services.smartfeatures;

import data.VehicleID;

public interface QRDecoder {
    VehicleID getVehicleID(String qrSimulated);
}
