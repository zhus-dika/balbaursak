package com.balbaursak.webapp.service.impl;

import com.balbaursak.webapp.service.ProduceService;
import com.balbaursak.webapp.domain.Produce;
import com.balbaursak.webapp.repository.ProduceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Produce}.
 */
@Service
@Transactional
public class ProduceServiceImpl implements ProduceService {

    private final Logger log = LoggerFactory.getLogger(ProduceServiceImpl.class);

    private final ProduceRepository produceRepository;

    public ProduceServiceImpl(ProduceRepository produceRepository) {
        this.produceRepository = produceRepository;
    }

    @Override
    public Produce save(Produce produce) {
        log.debug("Request to save Produce : {}", produce);
        return produceRepository.save(produce);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Produce> findAll(Pageable pageable) {
        log.debug("Request to get all Produces");
        return produceRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Produce> findOne(Long id) {
        log.debug("Request to get Produce : {}", id);
        return produceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produce : {}", id);
        produceRepository.deleteById(id);
    }
}
