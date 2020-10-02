package com.balbaursak.webapp.service.impl;

import com.balbaursak.webapp.service.RequestService;
import com.balbaursak.webapp.domain.Request;
import com.balbaursak.webapp.repository.RequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Request}.
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request save(Request request) {
        log.debug("Request to save Request : {}", request);
        return requestRepository.save(request);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Request> findAll(Pageable pageable) {
        log.debug("Request to get all Requests");
        return requestRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Request> findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        return requestRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.deleteById(id);
    }
}
