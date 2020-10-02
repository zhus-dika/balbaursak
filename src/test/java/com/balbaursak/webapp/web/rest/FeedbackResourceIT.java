package com.balbaursak.webapp.web.rest;

import com.balbaursak.webapp.BalbaursakApp;
import com.balbaursak.webapp.domain.Feedback;
import com.balbaursak.webapp.domain.Produce;
import com.balbaursak.webapp.repository.FeedbackRepository;
import com.balbaursak.webapp.service.FeedbackService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FeedbackResource} REST controller.
 */
@SpringBootTest(classes = BalbaursakApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FeedbackResourceIT {

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CUSTOMER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER = "BBBBBBBBBB";

    private static final Integer DEFAULT_VOTE = 0;
    private static final Integer UPDATED_VOTE = 1;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedbackMockMvc;

    private Feedback feedback;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createEntity(EntityManager em) {
        Feedback feedback = new Feedback()
            .created(DEFAULT_CREATED)
            .customer(DEFAULT_CUSTOMER)
            .vote(DEFAULT_VOTE)
            .content(DEFAULT_CONTENT);
        // Add required entity
        Produce produce;
        if (TestUtil.findAll(em, Produce.class).isEmpty()) {
            produce = ProduceResourceIT.createEntity(em);
            em.persist(produce);
            em.flush();
        } else {
            produce = TestUtil.findAll(em, Produce.class).get(0);
        }
        feedback.setProduce(produce);
        return feedback;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createUpdatedEntity(EntityManager em) {
        Feedback feedback = new Feedback()
            .created(UPDATED_CREATED)
            .customer(UPDATED_CUSTOMER)
            .vote(UPDATED_VOTE)
            .content(UPDATED_CONTENT);
        // Add required entity
        Produce produce;
        if (TestUtil.findAll(em, Produce.class).isEmpty()) {
            produce = ProduceResourceIT.createUpdatedEntity(em);
            em.persist(produce);
            em.flush();
        } else {
            produce = TestUtil.findAll(em, Produce.class).get(0);
        }
        feedback.setProduce(produce);
        return feedback;
    }

    @BeforeEach
    public void initTest() {
        feedback = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeedback() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();
        // Create the Feedback
        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isCreated());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate + 1);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testFeedback.getCustomer()).isEqualTo(DEFAULT_CUSTOMER);
        assertThat(testFeedback.getVote()).isEqualTo(DEFAULT_VOTE);
        assertThat(testFeedback.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createFeedbackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();

        // Create the Feedback with an existing ID
        feedback.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setCreated(null);

        // Create the Feedback, which fails.


        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setCustomer(null);

        // Create the Feedback, which fails.


        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setVote(null);

        // Create the Feedback, which fails.


        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setContent(null);

        // Create the Feedback, which fails.


        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeedbacks() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList
        restFeedbackMockMvc.perform(get("/api/feedbacks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER)))
            .andExpect(jsonPath("$.[*].vote").value(hasItem(DEFAULT_VOTE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", feedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedback.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.customer").value(DEFAULT_CUSTOMER))
            .andExpect(jsonPath("$.vote").value(DEFAULT_VOTE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }
    @Test
    @Transactional
    public void getNonExistingFeedback() throws Exception {
        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedback() throws Exception {
        // Initialize the database
        feedbackService.save(feedback);

        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback
        Feedback updatedFeedback = feedbackRepository.findById(feedback.getId()).get();
        // Disconnect from session so that the updates on updatedFeedback are not directly saved in db
        em.detach(updatedFeedback);
        updatedFeedback
            .created(UPDATED_CREATED)
            .customer(UPDATED_CUSTOMER)
            .vote(UPDATED_VOTE)
            .content(UPDATED_CONTENT);

        restFeedbackMockMvc.perform(put("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeedback)))
            .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testFeedback.getCustomer()).isEqualTo(UPDATED_CUSTOMER);
        assertThat(testFeedback.getVote()).isEqualTo(UPDATED_VOTE);
        assertThat(testFeedback.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingFeedback() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackMockMvc.perform(put("/api/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeedback() throws Exception {
        // Initialize the database
        feedbackService.save(feedback);

        int databaseSizeBeforeDelete = feedbackRepository.findAll().size();

        // Delete the feedback
        restFeedbackMockMvc.perform(delete("/api/feedbacks/{id}", feedback.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
