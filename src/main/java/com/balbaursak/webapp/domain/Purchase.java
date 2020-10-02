package com.balbaursak.webapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Purchase.
 */
@Entity
@Table(name = "purchase")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "created", nullable = false)
    private LocalDate created;

    @NotNull
    @Min(value = 0)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Min(value = 0)
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @Min(value = 0)
    @Column(name = "total", nullable = false)
    private Integer total;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "purchases", allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Purchase created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Purchase quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public Purchase price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotal() {
        return total;
    }

    public Purchase total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public Purchase product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Purchase)) {
            return false;
        }
        return id != null && id.equals(((Purchase) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Purchase{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", total=" + getTotal() +
            "}";
    }
}
