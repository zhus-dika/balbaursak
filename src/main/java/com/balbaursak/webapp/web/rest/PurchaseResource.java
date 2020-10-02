package com.balbaursak.webapp.web.rest;

import com.balbaursak.webapp.domain.Purchase;
import com.balbaursak.webapp.service.PurchaseService;
import com.balbaursak.webapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.balbaursak.webapp.domain.Purchase}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseResource.class);

    private static final String ENTITY_NAME = "purchase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchaseService purchaseService;

    public PurchaseResource(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    /**
     * {@code POST  /purchases} : Create a new purchase.
     *
     * @param purchase the purchase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchase, or with status {@code 400 (Bad Request)} if the purchase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchases")
    public ResponseEntity<Purchase> createPurchase(@Valid @RequestBody Purchase purchase) throws URISyntaxException {
        log.debug("REST request to save Purchase : {}", purchase);
        if (purchase.getId() != null) {
            throw new BadRequestAlertException("A new purchase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Purchase result = purchaseService.save(purchase);
        return ResponseEntity.created(new URI("/api/purchases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchases} : Updates an existing purchase.
     *
     * @param purchase the purchase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchase,
     * or with status {@code 400 (Bad Request)} if the purchase is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchases")
    public ResponseEntity<Purchase> updatePurchase(@Valid @RequestBody Purchase purchase) throws URISyntaxException {
        log.debug("REST request to update Purchase : {}", purchase);
        if (purchase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Purchase result = purchaseService.save(purchase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchase.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purchases} : get all the purchases.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchases in body.
     */
    @GetMapping("/purchases")
    public ResponseEntity<List<Purchase>> getAllPurchases(Pageable pageable) {
        log.debug("REST request to get a page of Purchases");
        Page<Purchase> page = purchaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /purchases/:id} : get the "id" purchase.
     *
     * @param id the id of the purchase to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchase, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchases/{id}")
    public ResponseEntity<Purchase> getPurchase(@PathVariable Long id) {
        log.debug("REST request to get Purchase : {}", id);
        Optional<Purchase> purchase = purchaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchase);
    }

    /**
     * {@code DELETE  /purchases/:id} : delete the "id" purchase.
     *
     * @param id the id of the purchase to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchases/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        log.debug("REST request to delete Purchase : {}", id);
        purchaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
