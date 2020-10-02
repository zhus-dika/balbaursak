package com.balbaursak.webapp.service;

import com.balbaursak.webapp.domain.Request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Request}.
 */
public interface RequestService {

    /**
     * Save a request.
     *
     * @param request the entity to save.
     * @return the persisted entity.
     */
    Request save(Request request);

    /**
     * Get all the requests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Request> findAll(Pageable pageable);


    /**
     * Get the "id" request.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Request> findOne(Long id);

    /**
     * Delete the "id" request.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
