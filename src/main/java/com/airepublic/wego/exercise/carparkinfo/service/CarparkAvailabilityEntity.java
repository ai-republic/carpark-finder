package com.airepublic.wego.exercise.carparkinfo.service;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * The entity holding all necessary data for an available carpark.
 */
@Entity
@Table(name = "TBL_CARPARK_AVAILABILITY")
public class CarparkAvailabilityEntity {
    @Id
    private String carparkNumber;
    @Column(nullable = false)
    private int totalLots;
    @Column(nullable = false)
    private int availableLots;
    @OneToOne
    @JoinColumn(name = "info_carparkNumber", referencedColumnName = "carparkNumber")
    private CarparkInfoEntity carparkInfo;

    /**
     * Constructor.
     */
    public CarparkAvailabilityEntity() {
    }


    /**
     * Constructor.
     * 
     * @param carparkNumber the carpark number
     * @param totalLots the total number of lots
     * @param availableLots the available number of lots
     * @param carparkInfo the {@link CarparkInfoEntity}
     */
    public CarparkAvailabilityEntity(final String carparkNumber, final int totalLots, final int availableLots, final CarparkInfoEntity carparkInfo) {
        this.carparkNumber = carparkNumber;
        this.totalLots = totalLots;
        this.availableLots = availableLots;
        this.carparkInfo = carparkInfo;
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


    /**
     * Gets the {@link CarparkInfoEntity} object associated with this carpark.
     *
     * @return the carparkInfo the {@link CarparkInfoEntity} object associated with this carpark
     */
    public CarparkInfoEntity getCarparkInfo() {
        return carparkInfo;
    }


    /**
     * Sets the {@link CarparkInfoEntity} object associated with this carpark.
     *
     * @param carparkInfo the {@link CarparkInfoEntity} object associated with this carpark
     */
    public void setCarparkInfo(final CarparkInfoEntity carparkInfo) {
        this.carparkInfo = carparkInfo;
    }


    @Override
    public int hashCode() {
        return Objects.hash(availableLots, carparkInfo, carparkNumber, totalLots);
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
        final CarparkAvailabilityEntity other = (CarparkAvailabilityEntity) obj;
        return availableLots == other.availableLots && Objects.equals(carparkInfo, other.carparkInfo) && Objects.equals(carparkNumber, other.carparkNumber) && totalLots == other.totalLots;
    }

}
