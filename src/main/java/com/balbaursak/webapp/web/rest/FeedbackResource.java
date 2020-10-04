package com.balbaursak.webapp.web.rest;

import com.balbaursak.webapp.domain.Feedback;
import com.balbaursak.webapp.domain.Produce;
import com.balbaursak.webapp.service.FeedbackService;
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
 * REST controller for managing {@link com.balbaursak.webapp.domain.Feedback}.
 */
@RestController
@RequestMapping("/api")
public class FeedbackResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackResource.class);

    private static final String ENTITY_NAME = "feedback";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedbackService feedbackService;
    private final ProduceService produceService;

    public FeedbackResource(FeedbackService feedbackService, ProduceService produceService) {
        this.feedbackService = feedbackService;
        this.produceService = produceService;
    }

    /**
     * {@code POST  /feedbacks} : Create a new feedback.
     *
     * @param feedback the feedback to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedback, or with status {@code 400 (Bad Request)} if the feedback has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedbacks")
    public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody Feedback feedback) throws URISyntaxException {
        log.debug("REST request to save Feedback : {}", feedback);
        if (feedback.getId() != null) {
            throw new BadRequestAlertException("A new feedback cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Feedback result = feedbackService.save(feedback);
        return ResponseEntity.created(new URI("/api/feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedbacks} : Updates an existing feedback.
     *
     * @param feedback the feedback to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedback,
     * or with status {@code 400 (Bad Request)} if the feedback is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedback couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedbacks")
    public ResponseEntity<Feedback> updateFeedback(@Valid @RequestBody Feedback feedback) throws URISyntaxException {
        log.debug("REST request to update Feedback : {}", feedback);
        if (feedback.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Feedback result = feedbackService.save(feedback);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedback.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /feedbacks} : get all the feedbacks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbacks in body.
     */
    @GetMapping("/feedbacks")
    public ResponseEntity<List<Feedback>> getAllFeedbacks(Pageable pageable) {
        log.debug("REST request to get a page of Feedbacks");
        Page<Feedback> page = feedbackService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/feedbacks/produce/{produceId}")
    public ResponseEntity<List<Feedback>> getAllFeedbacks(Pageable pageable, @PathVariable Long produceId) {
        log.debug("REST request to get a page of Feedbacks of the Produce");
        Produce produce = produceService.findOne(produceId).get();
        Page<Feedback> page = feedbackService.findAllByProduce(pageable, produce);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feedbacks/:id} : get the "id" feedback.
     *
     * @param id the id of the feedback to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedback, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<Feedback> getFeedback(@PathVariable Long id) {
        log.debug("REST request to get Feedback : {}", id);
        Optional<Feedback> feedback = feedbackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedback);
    }

    /**
     * {@code DELETE  /feedbacks/:id} : delete the "id" feedback.
     *
     * @param id the id of the feedback to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
