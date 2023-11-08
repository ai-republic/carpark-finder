package com.airepublic.wego.exercise.carparkinfo.service;

/**
 * The data object to be returned by the controller referring to one available carpark.
 */
public class CarparkAvailabilityDTO {
    private String address;
    private double latitude;
    private double longitude;
    private int totalLots;
    private int availableLots;

    /**
     * Constructor.
     */
    public CarparkAvailabilityDTO() {
    }


    /**
     * Constructor.
     * 
     * @param address the address of the carpark
     * @param latitude the latitude
     * @param longitude the longitude
     * @param totalLots the number of total lots
     * @param availableLots the number of available lots
     */
    public CarparkAvailabilityDTO(final String address, final double latitude, final double longitude, final int totalLots, final int availableLots) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalLots = totalLots;
        this.availableLots = availableLots;
    }


    /**
     * Gets the address of the parking space.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }


    /**
     * Sets the address of the parking space.
     *
     * @param address the address to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }


    /**
     * Gets the latitude of the parking space.
     *
     * @return the latitiude
     */
    public double getLatitude() {
        return latitude;
    }


    /**
     * Sets the latitude of the parking space.
     *
     * @param latitude the latitude to set
     */
    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }


    /**
     * Gets the longitude of the parking space.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }


    /**
     * Sets the longitude of the parking space.
     *
     * @param longitude the longitude to set
     */
    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }


    /**
     * Gets the number of total lots available of the parking space.
     * 
     * @return the total_lots
     */
    public int getTotalLots() {
        return totalLots;
    }


    /**
     * Sets the number of total lots of the parking space.
     *
     * @param total_lots the total_lots to set
     */
    public void setTotalLots(final int total_lots) {
        totalLots = total_lots;
    }


    /**
     * Gets the number of available lots of the parking space.
     *
     * @return the available lots
     */
    public int getAvailableLots() {
        return availableLots;
    }


    /**
     * Gets the number of available lots.
     *
     * @param available_lots the available lots to set
     */
    public void setAvailableLots(final int available_lots) {
        availableLots = available_lots;
    }
}
