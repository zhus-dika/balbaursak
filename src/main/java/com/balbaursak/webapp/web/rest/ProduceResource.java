package com.balbaursak.webapp.web.rest;

import com.balbaursak.webapp.domain.Produce;
import com.balbaursak.webapp.service.ProduceService;
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
 * REST controller for managing {@link com.balbaursak.webapp.domain.Produce}.
 */
@RestController
@RequestMapping("/api")
public class ProduceResource {

    private final Logger log = LoggerFactory.getLogger(ProduceResource.class);

    private static final String ENTITY_NAME = "produce";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduceService produceService;

    public ProduceResource(ProduceService produceService) {
        this.produceService = produceService;
    }

    /**
     * {@code POST  /produces} : Create a new produce.
     *
     * @param produce the produce to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produce, or with status {@code 400 (Bad Request)} if the produce has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produces")
    public ResponseEntity<Produce> createProduce(@Valid @RequestBody Produce produce) throws URISyntaxException {
        log.debug("REST request to save Produce : {}", produce);
        if (produce.getId() != null) {
            throw new BadRequestAlertException("A new produce cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Produce result = produceService.save(produce);
        return ResponseEntity.created(new URI("/api/produces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produces} : Updates an existing produce.
     *
     * @param produce the produce to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produce,
     * or with status {@code 400 (Bad Request)} if the produce is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produce couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produces")
    public ResponseEntity<Produce> updateProduce(@Valid @RequestBody Produce produce) throws URISyntaxException {
        log.debug("REST request to update Produce : {}", produce);
        if (produce.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Produce result = produceService.save(produce);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produce.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /produces} : get all the produces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produces in body.
     */
    @GetMapping("/produces")
    public ResponseEntity<List<Produce>> getAllProduces(Pageable pageable) {
        log.debug("REST request to get a page of Produces");
        Page<Produce> page = produceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /produces/:id} : get the "id" produce.
     *
     * @param id the id of the produce to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produce, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produces/{id}")
    public ResponseEntity<Produce> getProduce(@PathVariable Long id) {
        log.debug("REST request to get Produce : {}", id);
        Optional<Produce> produce = produceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produce);
    }

    /**
     * {@code DELETE  /produces/:id} : delete the "id" produce.
     *
     * @param id the id of the produce to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produces/{id}")
    public ResponseEntity<Void> deleteProduce(@PathVariable Long id) {
        log.debug("REST request to delete Produce : {}", id);
        produceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
