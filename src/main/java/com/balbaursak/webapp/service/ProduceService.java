package com.balbaursak.webapp.service;

import com.balbaursak.webapp.domain.Produce;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Produce}.
 */
public interface ProduceService {

    /**
     * Save a produce.
     *
     * @param produce the entity to save.
     * @return the persisted entity.
     */
    Produce save(Produce produce);

    /**
     * Get all the produces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Produce> findAll(Pageable pageable);


    /**
     * Get the "id" produce.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Produce> findOne(Long id);

    /**
     * Delete the "id" produce.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
