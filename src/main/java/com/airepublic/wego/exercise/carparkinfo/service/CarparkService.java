package com.airepublic.wego.exercise.carparkinfo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service class to provide information around carparks.
 */
@Service
public class CarparkService {
    @Autowired
    private CarparkAvailabilityRepository repository;

    /**
     * Gets the available carparks nearest to the specified latitude and longitude.
     *
     * @param latitude the latitude
     * @param longitude the longitude
     * @param page the page number
     * @param length the number of results
     * @return a list of carparks orderd by distance
     */
    public List<CarparkAvailabilityDTO> getAvailableCarparks(final double latitude, final double longitude, final int page, final int length) {
        // query available lots
        final List<CarparkAvailabilityEntity> result = repository.findTableCarparkAvailabilityByLocation(latitude, longitude, 100, PageRequest.of(page, length));
        // convert to view objects
        final List<CarparkAvailabilityDTO> dtos = new ArrayList<>();

        for (final CarparkAvailabilityEntity entity : result) {
            dtos.add(new CarparkAvailabilityDTO(entity.getCarparkInfo().getAddress(), entity.getCarparkInfo().getLatitude(), entity.getCarparkInfo().getLongitude(), entity.getTotalLots(), entity.getAvailableLots()));
        }

        return dtos;
    }
}
