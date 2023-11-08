package com.airepublic.wego.exercise.carparkinfo.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@link Repository} to access {@link CarparkInfoEntity} entities.
 */
@Repository
public interface CarparkInfoRepository extends JpaRepository<CarparkInfoEntity, Long> {
}
