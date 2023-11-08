package com.airepublic.wego.exercise.carparkinfo.service;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The entity holding all necessary data for a carpark.
 */
@Entity
@Table(name = "TBL_CARPARK_INFO")
public class CarparkInfoEntity {
    @Id
    private String carparkNumber;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;

    /**
     * Constructor.
     */
    public CarparkInfoEntity() {
    }


    /**
     * Constructor.
     *
     * @param carparkNumber the carpark number
     */
    public CarparkInfoEntity(final String carparkNumber) {
        this.carparkNumber = carparkNumber;
    }


    /**
     * Constructor.
     * 
     * @param carparkNumber the carpark number
     * @param address the address of the carpark
     * @param latitude the latitude
     * @param longitude the longitude
     */
    public CarparkInfoEntity(final String carparkNumber, final String address, final double latitude, final double longitude) {
        this.carparkNumber = carparkNumber;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     * Gets the carpark number.
     *
     * @return the carparkNumber
     */
    public String getCarparkNumber() {
        return carparkNumber;
    }


    /**
     * Sets the carpark number.
     *
     * @param carparkNumber the carparkNumber to set
     */
    public void setCarparkNumber(final String carparkNumber) {
        this.carparkNumber = carparkNumber;
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


    @Override
    public int hashCode() {
        return Objects.hash(carparkNumber);
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
        final CarparkInfoEntity other = (CarparkInfoEntity) obj;
        return Objects.equals(carparkNumber, other.carparkNumber);
    }
}
