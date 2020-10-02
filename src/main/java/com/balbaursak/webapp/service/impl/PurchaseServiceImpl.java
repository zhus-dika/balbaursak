package com.balbaursak.webapp.service.impl;

import com.balbaursak.webapp.service.PurchaseService;
import com.balbaursak.webapp.domain.Purchase;
import com.balbaursak.webapp.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Purchase}.
 */
@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final Logger log = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    private final PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public Purchase save(Purchase purchase) {
        log.debug("Request to save Purchase : {}", purchase);
        return purchaseRepository.save(purchase);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Purchase> findAll(Pageable pageable) {
        log.debug("Request to get all Purchases");
        return purchaseRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> findOne(Long id) {
        log.debug("Request to get Purchase : {}", id);
        return purchaseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Purchase : {}", id);
        purchaseRepository.deleteById(id);
    }
}
