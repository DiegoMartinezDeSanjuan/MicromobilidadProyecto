package data;

import exceptions.InvalidPairingArgsException;

/**
 * Clase esencial que representa un punto geográfico con latitud y longitud.
 */
public final class GeographicPoint {

    private static final float MIN_LATITUDE = -90.0f;
    private static final float MAX_LATITUDE = 90.0f;
    private static final float MIN_LONGITUDE = -180.0f;
    private static final float MAX_LONGITUDE = 180.0f;

    private final float latitude;
    private final float longitude;

    /**
     * Constructor con validación para latitud y longitud.
     *
     * @param lat Latitud en el rango [-90, 90].
     * @param lon Longitud en el rango [-180, 180].
     * @throws InvalidPairingArgsException Si lat o lon están fuera de rango.
     */
    public GeographicPoint(float lat, float lon) throws InvalidPairingArgsException {
        validarLatitud(lat);
        validarLongitud(lon);
        this.latitude = lat;
        this.longitude = lon;
    }

    /**
     * Getter para la latitud.
     *
     * @return El valor de la latitud.
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Getter para la longitud.
     *
     * @return El valor de la longitud.
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Valida el valor de la latitud.
     *
     * @param lat Latitud a validar.
     * @throws InvalidPairingArgsException Si la latitud está fuera de rango.
     */
    private void validarLatitud(float lat) throws InvalidPairingArgsException {
        if (lat < MIN_LATITUDE || lat > MAX_LATITUDE) {
            throw new InvalidPairingArgsException("La latitud debe estar entre " + MIN_LATITUDE + " y " + MAX_LATITUDE + " grados.");
        }
    }

    /**
     * Valida el valor de la longitud.
     *
     * @param lon Longitud a validar.
     * @throws InvalidPairingArgsException Si la longitud está fuera de rango.
     */
    private void validarLongitud(float lon) throws InvalidPairingArgsException {
        if (lon < MIN_LONGITUDE || lon > MAX_LONGITUDE) {
            throw new InvalidPairingArgsException("La longitud debe estar entre " + MIN_LONGITUDE + " y " + MAX_LONGITUDE + " grados.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicPoint that = (GeographicPoint) o;
        return Float.compare(that.latitude, latitude) == 0 &&
                Float.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "GeographicPoint {" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
