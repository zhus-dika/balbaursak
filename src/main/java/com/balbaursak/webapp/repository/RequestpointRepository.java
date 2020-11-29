package com.balbaursak.webapp.repository;

import com.balbaursak.webapp.domain.Requestpoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import com.balbaursak.webapp.domain.Request;
/**
 * Spring Data  repository for the Requestpoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestpointRepository extends JpaRepository<Requestpoint, Long> {
    Page<Requestpoint> findAllByRequest(Pageable pageable, Request request);
}
