package com.airepublic.wego.exercise.carparkinfo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The {@link Repository} to access {@link CarparkInfoEntity} entities.
 */
@Repository
public interface CarparkAvailabilityRepository extends JpaRepository<CarparkAvailabilityEntity, Long> {
    String HAVERSINE_PART = "(6371 * acos(cos(radians(:latitude)) * cos(radians(n.latitude)) * cos(radians(n.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(n.latitude))))";

    /**
     * Query to find the nearest carparks based on the specified latitude and longitude, the radius
     * distance and paging information.
     *
     * @param latitude the latitude
     * @param longitude the longitude
     * @param distance the distance
     * @param pageable the paging information
     * @return a list of {@link CarparkAvailabilityEntity} entities
     */
    @Query("SELECT m FROM CarparkAvailabilityEntity m, CarparkInfoEntity n WHERE m.availableLots != 0 AND " + HAVERSINE_PART + " < :distance ORDER BY " + HAVERSINE_PART + " DESC")
    List<CarparkAvailabilityEntity> findTableCarparkAvailabilityByLocation(@Param("latitude") final double latitude, @Param("longitude") final double longitude, @Param("distance") final int distance, Pageable pageable);
}
