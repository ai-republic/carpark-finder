package com.airepublic.wego.exercise.carparkinfo.util;

/**
 * Class to represent a latitude/longitude coordinate.
 */
public class LatLonCoordinate {
    private final double latitude;
    private final double longitude;

    /**
     * Constructor.
     *
     * @param latitude the latitude
     * @param longitude the longitude
     */
    public LatLonCoordinate(final double latitude, final double longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     * Gets the latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }


    /**
     * Gets the longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LatLonCoordinate other = (LatLonCoordinate) obj;
        if (Double.doubleToLongBits(latitude) != Double
                .doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(longitude) != Double
                .doubleToLongBits(other.longitude)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "LatLonCoordinate [latitude=" + latitude + ", longitude=" + longitude + "]";
    }
}