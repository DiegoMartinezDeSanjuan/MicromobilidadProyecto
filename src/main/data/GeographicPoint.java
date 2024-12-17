package data;

import exceptions.InvalidPairingArgsException;

/**
 * Essential data class representing a geographic point with latitude and longitude.
 */
public final class GeographicPoint {

    private final float latitude;
    private final float longitude;

    /**
     * Constructor with validation for latitude and longitude.
     *
     * @param lat Latitude in the range [-90, 90].
     * @param lon Longitude in the range [-180, 180].
     * @throws InvalidPairingArgsException If lat or lon are out of range.
     */
    public GeographicPoint(float lat, float lon) throws InvalidPairingArgsException {
        if (lat < -90 || lat > 90) {
            throw new InvalidPairingArgsException("Latitude must be between -90 and 90 degrees.");
        }
        if (lon < -180 || lon > 180) {
            throw new InvalidPairingArgsException("Longitude must be between -180 and 180 degrees.");
        }
        this.latitude = lat;
        this.longitude = lon;
    }

    /**
     * Getter for latitude.
     *
     * @return The latitude value.
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Getter for longitude.
     *
     * @return The longitude value.
     */
    public float getLongitude() {
        return longitude;
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
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(longitude);
        return result;
    }

    @Override
    public String toString() {
        return "GeographicPoint {" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
