package com.balbaursak.webapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Requestpoint.
 */
@Entity
@Table(name = "requestpoint")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Requestpoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Min(value = 0)
    @Column(name = "total", nullable = false)
    private Integer total;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "requestpoints", allowSetters = true)
    private Produce produce;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "requestpoints", allowSetters = true)
    private Request request;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Requestpoint quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotal() {
        return total;
    }

    public Requestpoint total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Produce getProduce() {
        return produce;
    }

    public Requestpoint produce(Produce produce) {
        this.produce = produce;
        return this;
    }

    public void setProduce(Produce produce) {
        this.produce = produce;
    }

    public Request getRequest() {
        return request;
    }

    public Requestpoint request(Request request) {
        this.request = request;
        return this;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requestpoint)) {
            return false;
        }
        return id != null && id.equals(((Requestpoint) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Requestpoint{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", total=" + getTotal() +
            "}";
    }
}
