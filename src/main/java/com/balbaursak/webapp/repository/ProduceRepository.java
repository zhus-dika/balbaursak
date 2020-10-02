package com.balbaursak.webapp.repository;

import com.balbaursak.webapp.domain.Produce;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Produce entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduceRepository extends JpaRepository<Produce, Long> {
}
