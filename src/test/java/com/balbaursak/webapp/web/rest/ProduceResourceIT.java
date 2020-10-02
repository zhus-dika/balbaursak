package com.balbaursak.webapp.web.rest;

import com.balbaursak.webapp.BalbaursakApp;
import com.balbaursak.webapp.domain.Produce;
import com.balbaursak.webapp.domain.Category;
import com.balbaursak.webapp.repository.ProduceRepository;
import com.balbaursak.webapp.service.ProduceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProduceResource} REST controller.
 */
@SpringBootTest(classes = BalbaursakApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProduceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTAINS = "AAAAAAAAAA";
    private static final String UPDATED_CONTAINS = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAYS = 0;
    private static final Integer UPDATED_DAYS = 1;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 0;
    private static final Integer UPDATED_PRICE = 1;

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private ProduceRepository produceRepository;

    @Autowired
    private ProduceService produceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProduceMockMvc;

    private Produce produce;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produce createEntity(EntityManager em) {
        Produce produce = new Produce()
            .name(DEFAULT_NAME)
            .unit(DEFAULT_UNIT)
            .contains(DEFAULT_CONTAINS)
            .days(DEFAULT_DAYS)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        produce.setCategory(category);
        return produce;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produce createUpdatedEntity(EntityManager em) {
        Produce produce = new Produce()
            .name(UPDATED_NAME)
            .unit(UPDATED_UNIT)
            .contains(UPDATED_CONTAINS)
            .days(UPDATED_DAYS)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        produce.setCategory(category);
        return produce;
    }

    @BeforeEach
    public void initTest() {
        produce = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduce() throws Exception {
        int databaseSizeBeforeCreate = produceRepository.findAll().size();
        // Create the Produce
        restProduceMockMvc.perform(post("/api/produces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produce)))
            .andExpect(status().isCreated());

        // Validate the Produce in the database
        List<Produce> produceList = produceRepository.findAll();
        assertThat(produceList).hasSize(databaseSizeBeforeCreate + 1);
        Produce testProduce = produceList.get(produceList.size() - 1);
        assertThat(testProduce.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduce.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testProduce.getContains()).isEqualTo(DEFAULT_CONTAINS);
        assertThat(testProduce.getDays()).isEqualTo(DEFAULT_DAYS);
        assertThat(testProduce.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduce.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduce.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testProduce.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createProduceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produceRepository.findAll().size();

        // Create the Produce with an existing ID
        produce.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduceMockMvc.perform(post("/api/produces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produce)))
            .andExpect(status().isBadRequest());

        // Validate the Produce in the database
        List<Produce> produceList = produceRepository.findAll();
        assertThat(produceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = produceRepository.findAll().size();
        // set the field null
        produce.setName(null);

        // Create the Produce, which fails.


        restProduceMockMvc.perform(post("/api/produces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produce)))
            .andExpect(status().isBadRequest());

        List<Produce> produceList = produceRepository.findAll();
        assertThat(produceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = produceRepository.findAll().size();
        // set the field null
        produce.setUnit(null);

        // Create the Produce, which fails.


        restProduceMockMvc.perform(post("/api/produces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produce)))
            .andExpect(status().isBadRequest());

        List<Produce> produceList = produceRepository.findAll();
        assertThat(produceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = produceRepository.findAll().size();
        // set the field null
        produce.setDays(null);

        // Create the Produce, which fails.


        restProduceMockMvc.perform(post("/api/produces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produce)))
            .andExpect(status().isBadRequest());

        List<Produce> produceList = produceRepository.findAll();
        assertThat(produceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = produceRepository.findAll().size();
        // set the field null
        produce.setPrice(null);

        // Create the Produce, which fails.


        restProduceMockMvc.perform(post("/api/produces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produce)))
            .andExpect(status().isBadRequest());

        List<Produce> produceList = produceRepository.findAll();
        assertThat(produceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProduces() throws Exception {
        // Initialize the database
        produceRepository.saveAndFlush(produce);

        // Get all the produceList
        restProduceMockMvc.perform(get("/api/produces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produce.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].contains").value(hasItem(DEFAULT_CONTAINS.toString())))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    @Transactional
    public void getProduce() throws Exception {
        // Initialize the database
        produceRepository.saveAndFlush(produce);

        // Get the produce
        restProduceMockMvc.perform(get("/api/produces/{id}", produce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produce.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.contains").value(DEFAULT_CONTAINS.toString()))
            .andExpect(jsonPath("$.days").value(DEFAULT_DAYS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }
    @Test
    @Transactional
    public void getNonExistingProduce() throws Exception {
        // Get the produce
        restProduceMockMvc.perform(get("/api/produces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduce() throws Exception {
        // Initialize the database
        produceService.save(produce);

        int databaseSizeBeforeUpdate = produceRepository.findAll().size();

        // Update the produce
        Produce updatedProduce = produceRepository.findById(produce.getId()).get();
        // Disconnect from session so that the updates on updatedProduce are not directly saved in db
        em.detach(updatedProduce);
        updatedProduce
            .name(UPDATED_NAME)
            .unit(UPDATED_UNIT)
            .contains(UPDATED_CONTAINS)
            .days(UPDATED_DAYS)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);

        restProduceMockMvc.perform(put("/api/produces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduce)))
            .andExpect(status().isOk());

        // Validate the Produce in the database
        List<Produce> produceList = produceRepository.findAll();
        assertThat(produceList).hasSize(databaseSizeBeforeUpdate);
        Produce testProduce = produceList.get(produceList.size() - 1);
        assertThat(testProduce.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduce.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testProduce.getContains()).isEqualTo(UPDATED_CONTAINS);
        assertThat(testProduce.getDays()).isEqualTo(UPDATED_DAYS);
        assertThat(testProduce.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduce.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduce.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testProduce.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProduce() throws Exception {
        int databaseSizeBeforeUpdate = produceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduceMockMvc.perform(put("/api/produces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produce)))
            .andExpect(status().isBadRequest());

        // Validate the Produce in the database
        List<Produce> produceList = produceRepository.findAll();
        assertThat(produceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduce() throws Exception {
        // Initialize the database
        produceService.save(produce);

        int databaseSizeBeforeDelete = produceRepository.findAll().size();

        // Delete the produce
        restProduceMockMvc.perform(delete("/api/produces/{id}", produce.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produce> produceList = produceRepository.findAll();
        assertThat(produceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
