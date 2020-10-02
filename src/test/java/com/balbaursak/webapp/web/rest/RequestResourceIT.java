package com.balbaursak.webapp.web.rest;

import com.balbaursak.webapp.BalbaursakApp;
import com.balbaursak.webapp.domain.Request;
import com.balbaursak.webapp.repository.RequestRepository;
import com.balbaursak.webapp.service.RequestService;

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
 * Integration tests for the {@link RequestResource} REST controller.
 */
@SpringBootTest(classes = BalbaursakApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RequestResourceIT {

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CUSTOMER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL = 0;
    private static final Integer UPDATED_TOTAL = 1;

    private static final String DEFAULT_COMPLETE = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SALES = 0;
    private static final Integer UPDATED_SALES = 1;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestMockMvc;

    private Request request;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Request createEntity(EntityManager em) {
        Request request = new Request()
            .created(DEFAULT_CREATED)
            .customer(DEFAULT_CUSTOMER)
            .phone(DEFAULT_PHONE)
            .total(DEFAULT_TOTAL)
            .complete(DEFAULT_COMPLETE)
            .sales(DEFAULT_SALES)
            .address(DEFAULT_ADDRESS);
        return request;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Request createUpdatedEntity(EntityManager em) {
        Request request = new Request()
            .created(UPDATED_CREATED)
            .customer(UPDATED_CUSTOMER)
            .phone(UPDATED_PHONE)
            .total(UPDATED_TOTAL)
            .complete(UPDATED_COMPLETE)
            .sales(UPDATED_SALES)
            .address(UPDATED_ADDRESS);
        return request;
    }

    @BeforeEach
    public void initTest() {
        request = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequest() throws Exception {
        int databaseSizeBeforeCreate = requestRepository.findAll().size();
        // Create the Request
        restRequestMockMvc.perform(post("/api/requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isCreated());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeCreate + 1);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testRequest.getCustomer()).isEqualTo(DEFAULT_CUSTOMER);
        assertThat(testRequest.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testRequest.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testRequest.getComplete()).isEqualTo(DEFAULT_COMPLETE);
        assertThat(testRequest.getSales()).isEqualTo(DEFAULT_SALES);
        assertThat(testRequest.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestRepository.findAll().size();

        // Create the Request with an existing ID
        request.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestMockMvc.perform(post("/api/requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRepository.findAll().size();
        // set the field null
        request.setCreated(null);

        // Create the Request, which fails.


        restRequestMockMvc.perform(post("/api/requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRepository.findAll().size();
        // set the field null
        request.setCustomer(null);

        // Create the Request, which fails.


        restRequestMockMvc.perform(post("/api/requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRepository.findAll().size();
        // set the field null
        request.setPhone(null);

        // Create the Request, which fails.


        restRequestMockMvc.perform(post("/api/requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRepository.findAll().size();
        // set the field null
        request.setTotal(null);

        // Create the Request, which fails.


        restRequestMockMvc.perform(post("/api/requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompleteIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRepository.findAll().size();
        // set the field null
        request.setComplete(null);

        // Create the Request, which fails.


        restRequestMockMvc.perform(post("/api/requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRepository.findAll().size();
        // set the field null
        request.setAddress(null);

        // Create the Request, which fails.


        restRequestMockMvc.perform(post("/api/requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequests() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList
        restRequestMockMvc.perform(get("/api/requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(request.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].complete").value(hasItem(DEFAULT_COMPLETE)))
            .andExpect(jsonPath("$.[*].sales").value(hasItem(DEFAULT_SALES)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getRequest() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get the request
        restRequestMockMvc.perform(get("/api/requests/{id}", request.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(request.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.customer").value(DEFAULT_CUSTOMER))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.complete").value(DEFAULT_COMPLETE))
            .andExpect(jsonPath("$.sales").value(DEFAULT_SALES))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingRequest() throws Exception {
        // Get the request
        restRequestMockMvc.perform(get("/api/requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequest() throws Exception {
        // Initialize the database
        requestService.save(request);

        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // Update the request
        Request updatedRequest = requestRepository.findById(request.getId()).get();
        // Disconnect from session so that the updates on updatedRequest are not directly saved in db
        em.detach(updatedRequest);
        updatedRequest
            .created(UPDATED_CREATED)
            .customer(UPDATED_CUSTOMER)
            .phone(UPDATED_PHONE)
            .total(UPDATED_TOTAL)
            .complete(UPDATED_COMPLETE)
            .sales(UPDATED_SALES)
            .address(UPDATED_ADDRESS);

        restRequestMockMvc.perform(put("/api/requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequest)))
            .andExpect(status().isOk());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testRequest.getCustomer()).isEqualTo(UPDATED_CUSTOMER);
        assertThat(testRequest.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testRequest.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testRequest.getComplete()).isEqualTo(UPDATED_COMPLETE);
        assertThat(testRequest.getSales()).isEqualTo(UPDATED_SALES);
        assertThat(testRequest.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestMockMvc.perform(put("/api/requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRequest() throws Exception {
        // Initialize the database
        requestService.save(request);

        int databaseSizeBeforeDelete = requestRepository.findAll().size();

        // Delete the request
        restRequestMockMvc.perform(delete("/api/requests/{id}", request.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
