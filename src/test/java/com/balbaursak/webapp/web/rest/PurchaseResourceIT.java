package com.balbaursak.webapp.web.rest;

import com.balbaursak.webapp.BalbaursakApp;
import com.balbaursak.webapp.domain.Purchase;
import com.balbaursak.webapp.domain.Product;
import com.balbaursak.webapp.repository.PurchaseRepository;
import com.balbaursak.webapp.service.PurchaseService;

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
 * Integration tests for the {@link PurchaseResource} REST controller.
 */
@SpringBootTest(classes = BalbaursakApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PurchaseResourceIT {

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;

    private static final Integer DEFAULT_PRICE = 0;
    private static final Integer UPDATED_PRICE = 1;

    private static final Integer DEFAULT_TOTAL = 0;
    private static final Integer UPDATED_TOTAL = 1;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchaseMockMvc;

    private Purchase purchase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Purchase createEntity(EntityManager em) {
        Purchase purchase = new Purchase()
            .created(DEFAULT_CREATED)
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE)
            .total(DEFAULT_TOTAL);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        purchase.setProduct(product);
        return purchase;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Purchase createUpdatedEntity(EntityManager em) {
        Purchase purchase = new Purchase()
            .created(UPDATED_CREATED)
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .total(UPDATED_TOTAL);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        purchase.setProduct(product);
        return purchase;
    }

    @BeforeEach
    public void initTest() {
        purchase = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchase() throws Exception {
        int databaseSizeBeforeCreate = purchaseRepository.findAll().size();
        // Create the Purchase
        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchase)))
            .andExpect(status().isCreated());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeCreate + 1);
        Purchase testPurchase = purchaseList.get(purchaseList.size() - 1);
        assertThat(testPurchase.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPurchase.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPurchase.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPurchase.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createPurchaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseRepository.findAll().size();

        // Create the Purchase with an existing ID
        purchase.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchase)))
            .andExpect(status().isBadRequest());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseRepository.findAll().size();
        // set the field null
        purchase.setCreated(null);

        // Create the Purchase, which fails.


        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchase)))
            .andExpect(status().isBadRequest());

        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseRepository.findAll().size();
        // set the field null
        purchase.setQuantity(null);

        // Create the Purchase, which fails.


        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchase)))
            .andExpect(status().isBadRequest());

        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseRepository.findAll().size();
        // set the field null
        purchase.setPrice(null);

        // Create the Purchase, which fails.


        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchase)))
            .andExpect(status().isBadRequest());

        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseRepository.findAll().size();
        // set the field null
        purchase.setTotal(null);

        // Create the Purchase, which fails.


        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchase)))
            .andExpect(status().isBadRequest());

        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchases() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        // Get all the purchaseList
        restPurchaseMockMvc.perform(get("/api/purchases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchase.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)));
    }
    
    @Test
    @Transactional
    public void getPurchase() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        // Get the purchase
        restPurchaseMockMvc.perform(get("/api/purchases/{id}", purchase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchase.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL));
    }
    @Test
    @Transactional
    public void getNonExistingPurchase() throws Exception {
        // Get the purchase
        restPurchaseMockMvc.perform(get("/api/purchases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchase() throws Exception {
        // Initialize the database
        purchaseService.save(purchase);

        int databaseSizeBeforeUpdate = purchaseRepository.findAll().size();

        // Update the purchase
        Purchase updatedPurchase = purchaseRepository.findById(purchase.getId()).get();
        // Disconnect from session so that the updates on updatedPurchase are not directly saved in db
        em.detach(updatedPurchase);
        updatedPurchase
            .created(UPDATED_CREATED)
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .total(UPDATED_TOTAL);

        restPurchaseMockMvc.perform(put("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchase)))
            .andExpect(status().isOk());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeUpdate);
        Purchase testPurchase = purchaseList.get(purchaseList.size() - 1);
        assertThat(testPurchase.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPurchase.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPurchase.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPurchase.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchase() throws Exception {
        int databaseSizeBeforeUpdate = purchaseRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseMockMvc.perform(put("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchase)))
            .andExpect(status().isBadRequest());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchase() throws Exception {
        // Initialize the database
        purchaseService.save(purchase);

        int databaseSizeBeforeDelete = purchaseRepository.findAll().size();

        // Delete the purchase
        restPurchaseMockMvc.perform(delete("/api/purchases/{id}", purchase.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
