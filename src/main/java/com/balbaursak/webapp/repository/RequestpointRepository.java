package com.balbaursak.webapp.repository;

import com.balbaursak.webapp.domain.Requestpoint;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Requestpoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestpointRepository extends JpaRepository<Requestpoint, Long> {
}
