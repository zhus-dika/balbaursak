package com.balbaursak.webapp.web.rest;

import com.balbaursak.webapp.domain.Requestpoint;
import com.balbaursak.webapp.service.RequestpointService;
import com.balbaursak.webapp.service.RequestService;
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
 * REST controller for managing {@link com.balbaursak.webapp.domain.Requestpoint}.
 */
@RestController
@RequestMapping("/api")
public class RequestpointResource {

    private final Logger log = LoggerFactory.getLogger(RequestpointResource.class);

    private static final String ENTITY_NAME = "requestpoint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestpointService requestpointService;

    private final RequestService requestService;

    public RequestpointResource(RequestpointService requestpointService, RequestService requestService) {
        this.requestpointService = requestpointService;
        this.requestService = requestService;
    }

    /**
     * {@code POST  /requestpoints} : Create a new requestpoint.
     *
     * @param requestpoint the requestpoint to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestpoint, or with status {@code 400 (Bad Request)} if the requestpoint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requestpoints")
    public ResponseEntity<Requestpoint> createRequestpoint(@Valid @RequestBody Requestpoint requestpoint) throws URISyntaxException {
        log.debug("REST request to save Requestpoint : {}", requestpoint);
        if (requestpoint.getId() != null) {
            throw new BadRequestAlertException("A new requestpoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Requestpoint result = requestpointService.save(requestpoint);
        return ResponseEntity.created(new URI("/api/requestpoints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requestpoints} : Updates an existing requestpoint.
     *
     * @param requestpoint the requestpoint to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestpoint,
     * or with status {@code 400 (Bad Request)} if the requestpoint is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestpoint couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requestpoints")
    public ResponseEntity<Requestpoint> updateRequestpoint(@Valid @RequestBody Requestpoint requestpoint) throws URISyntaxException {
        log.debug("REST request to update Requestpoint : {}", requestpoint);
        if (requestpoint.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Requestpoint result = requestpointService.save(requestpoint);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestpoint.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /requestpoints} : get all the requestpoints.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestpoints in body.
     */
    @GetMapping("/requestpoints")
    public ResponseEntity<List<Requestpoint>> getAllRequestpoints(Pageable pageable) {
        log.debug("REST request to get a page of Requestpoints");
        Page<Requestpoint> page = requestpointService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/requestpoints/points")
        public ResponseEntity<List<Requestpoint>> getAllRequestpointsByRequest(@RequestParam Long id, Pageable pageable) {
            log.debug("REST request to get a page of Requestpoints");
            Page<Requestpoint> page = requestpointService.findAllByRequest(pageable, requestService.findOne(id).get());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

    /**
     * {@code GET  /requestpoints/:id} : get the "id" requestpoint.
     *
     * @param id the id of the requestpoint to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestpoint, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requestpoints/{id}")
    public ResponseEntity<Requestpoint> getRequestpoint(@PathVariable Long id) {
        log.debug("REST request to get Requestpoint : {}", id);
        Optional<Requestpoint> requestpoint = requestpointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestpoint);
    }

    /**
     * {@code DELETE  /requestpoints/:id} : delete the "id" requestpoint.
     *
     * @param id the id of the requestpoint to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requestpoints/{id}")
    public ResponseEntity<Void> deleteRequestpoint(@PathVariable Long id) {
        log.debug("REST request to delete Requestpoint : {}", id);
        requestpointService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
