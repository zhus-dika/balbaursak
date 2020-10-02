package com.balbaursak.webapp.web.rest;

import com.balbaursak.webapp.BalbaursakApp;
import com.balbaursak.webapp.domain.Requestpoint;
import com.balbaursak.webapp.domain.Produce;
import com.balbaursak.webapp.domain.Request;
import com.balbaursak.webapp.repository.RequestpointRepository;
import com.balbaursak.webapp.service.RequestpointService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RequestpointResource} REST controller.
 */
@SpringBootTest(classes = BalbaursakApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RequestpointResourceIT {

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;

    private static final Integer DEFAULT_TOTAL = 0;
    private static final Integer UPDATED_TOTAL = 1;

    @Autowired
    private RequestpointRepository requestpointRepository;

    @Autowired
    private RequestpointService requestpointService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestpointMockMvc;

    private Requestpoint requestpoint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requestpoint createEntity(EntityManager em) {
        Requestpoint requestpoint = new Requestpoint()
            .quantity(DEFAULT_QUANTITY)
            .total(DEFAULT_TOTAL);
        // Add required entity
        Produce produce;
        if (TestUtil.findAll(em, Produce.class).isEmpty()) {
            produce = ProduceResourceIT.createEntity(em);
            em.persist(produce);
            em.flush();
        } else {
            produce = TestUtil.findAll(em, Produce.class).get(0);
        }
        requestpoint.setProduce(produce);
        // Add required entity
        Request request;
        if (TestUtil.findAll(em, Request.class).isEmpty()) {
            request = RequestResourceIT.createEntity(em);
            em.persist(request);
            em.flush();
        } else {
            request = TestUtil.findAll(em, Request.class).get(0);
        }
        requestpoint.setRequest(request);
        return requestpoint;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requestpoint createUpdatedEntity(EntityManager em) {
        Requestpoint requestpoint = new Requestpoint()
            .quantity(UPDATED_QUANTITY)
            .total(UPDATED_TOTAL);
        // Add required entity
        Produce produce;
        if (TestUtil.findAll(em, Produce.class).isEmpty()) {
            produce = ProduceResourceIT.createUpdatedEntity(em);
            em.persist(produce);
            em.flush();
        } else {
            produce = TestUtil.findAll(em, Produce.class).get(0);
        }
        requestpoint.setProduce(produce);
        // Add required entity
        Request request;
        if (TestUtil.findAll(em, Request.class).isEmpty()) {
            request = RequestResourceIT.createUpdatedEntity(em);
            em.persist(request);
            em.flush();
        } else {
            request = TestUtil.findAll(em, Request.class).get(0);
        }
        requestpoint.setRequest(request);
        return requestpoint;
    }

    @BeforeEach
    public void initTest() {
        requestpoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestpoint() throws Exception {
        int databaseSizeBeforeCreate = requestpointRepository.findAll().size();
        // Create the Requestpoint
        restRequestpointMockMvc.perform(post("/api/requestpoints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requestpoint)))
            .andExpect(status().isCreated());

        // Validate the Requestpoint in the database
        List<Requestpoint> requestpointList = requestpointRepository.findAll();
        assertThat(requestpointList).hasSize(databaseSizeBeforeCreate + 1);
        Requestpoint testRequestpoint = requestpointList.get(requestpointList.size() - 1);
        assertThat(testRequestpoint.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testRequestpoint.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createRequestpointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestpointRepository.findAll().size();

        // Create the Requestpoint with an existing ID
        requestpoint.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestpointMockMvc.perform(post("/api/requestpoints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requestpoint)))
            .andExpect(status().isBadRequest());

        // Validate the Requestpoint in the database
        List<Requestpoint> requestpointList = requestpointRepository.findAll();
        assertThat(requestpointList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestpointRepository.findAll().size();
        // set the field null
        requestpoint.setQuantity(null);

        // Create the Requestpoint, which fails.


        restRequestpointMockMvc.perform(post("/api/requestpoints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requestpoint)))
            .andExpect(status().isBadRequest());

        List<Requestpoint> requestpointList = requestpointRepository.findAll();
        assertThat(requestpointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestpointRepository.findAll().size();
        // set the field null
        requestpoint.setTotal(null);

        // Create the Requestpoint, which fails.


        restRequestpointMockMvc.perform(post("/api/requestpoints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requestpoint)))
            .andExpect(status().isBadRequest());

        List<Requestpoint> requestpointList = requestpointRepository.findAll();
        assertThat(requestpointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequestpoints() throws Exception {
        // Initialize the database
        requestpointRepository.saveAndFlush(requestpoint);

        // Get all the requestpointList
        restRequestpointMockMvc.perform(get("/api/requestpoints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestpoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)));
    }
    
    @Test
    @Transactional
    public void getRequestpoint() throws Exception {
        // Initialize the database
        requestpointRepository.saveAndFlush(requestpoint);

        // Get the requestpoint
        restRequestpointMockMvc.perform(get("/api/requestpoints/{id}", requestpoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestpoint.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL));
    }
    @Test
    @Transactional
    public void getNonExistingRequestpoint() throws Exception {
        // Get the requestpoint
        restRequestpointMockMvc.perform(get("/api/requestpoints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestpoint() throws Exception {
        // Initialize the database
        requestpointService.save(requestpoint);

        int databaseSizeBeforeUpdate = requestpointRepository.findAll().size();

        // Update the requestpoint
        Requestpoint updatedRequestpoint = requestpointRepository.findById(requestpoint.getId()).get();
        // Disconnect from session so that the updates on updatedRequestpoint are not directly saved in db
        em.detach(updatedRequestpoint);
        updatedRequestpoint
            .quantity(UPDATED_QUANTITY)
            .total(UPDATED_TOTAL);

        restRequestpointMockMvc.perform(put("/api/requestpoints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequestpoint)))
            .andExpect(status().isOk());

        // Validate the Requestpoint in the database
        List<Requestpoint> requestpointList = requestpointRepository.findAll();
        assertThat(requestpointList).hasSize(databaseSizeBeforeUpdate);
        Requestpoint testRequestpoint = requestpointList.get(requestpointList.size() - 1);
        assertThat(testRequestpoint.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testRequestpoint.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestpoint() throws Exception {
        int databaseSizeBeforeUpdate = requestpointRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestpointMockMvc.perform(put("/api/requestpoints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requestpoint)))
            .andExpect(status().isBadRequest());

        // Validate the Requestpoint in the database
        List<Requestpoint> requestpointList = requestpointRepository.findAll();
        assertThat(requestpointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRequestpoint() throws Exception {
        // Initialize the database
        requestpointService.save(requestpoint);

        int databaseSizeBeforeDelete = requestpointRepository.findAll().size();

        // Delete the requestpoint
        restRequestpointMockMvc.perform(delete("/api/requestpoints/{id}", requestpoint.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Requestpoint> requestpointList = requestpointRepository.findAll();
        assertThat(requestpointList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
