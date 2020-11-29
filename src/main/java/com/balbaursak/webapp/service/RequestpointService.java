package com.balbaursak.webapp.service;

import com.balbaursak.webapp.domain.Requestpoint;
import com.balbaursak.webapp.domain.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Requestpoint}.
 */
public interface RequestpointService {

    /**
     * Save a requestpoint.
     *
     * @param requestpoint the entity to save.
     * @return the persisted entity.
     */
    Requestpoint save(Requestpoint requestpoint);

    /**
     * Get all the requestpoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Requestpoint> findAll(Pageable pageable);

    Page<Requestpoint> findAllByRequest(Pageable pageable, Request request);
    /**
     * Get the "id" requestpoint.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Requestpoint> findOne(Long id);

    /**
     * Delete the "id" requestpoint.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
