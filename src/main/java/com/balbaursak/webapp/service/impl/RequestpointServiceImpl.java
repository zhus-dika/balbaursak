package com.balbaursak.webapp.service.impl;

import com.balbaursak.webapp.service.RequestpointService;
import com.balbaursak.webapp.domain.Requestpoint;
import com.balbaursak.webapp.repository.RequestpointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Requestpoint}.
 */
@Service
@Transactional
public class RequestpointServiceImpl implements RequestpointService {

    private final Logger log = LoggerFactory.getLogger(RequestpointServiceImpl.class);

    private final RequestpointRepository requestpointRepository;

    public RequestpointServiceImpl(RequestpointRepository requestpointRepository) {
        this.requestpointRepository = requestpointRepository;
    }

    @Override
    public Requestpoint save(Requestpoint requestpoint) {
        log.debug("Request to save Requestpoint : {}", requestpoint);
        return requestpointRepository.save(requestpoint);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Requestpoint> findAll(Pageable pageable) {
        log.debug("Request to get all Requestpoints");
        return requestpointRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Requestpoint> findOne(Long id) {
        log.debug("Request to get Requestpoint : {}", id);
        return requestpointRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Requestpoint : {}", id);
        requestpointRepository.deleteById(id);
    }
}
