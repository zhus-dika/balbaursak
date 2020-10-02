package com.balbaursak.webapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "created", nullable = false)
    private LocalDate created;

    @NotNull
    @Column(name = "customer", nullable = false)
    private String customer;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Min(value = 0)
    @Column(name = "total", nullable = false)
    private Integer total;

    @NotNull
    @Column(name = "complete", nullable = false)
    private String complete;

    @Min(value = 0)
    @Column(name = "sales")
    private Integer sales;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Requestpoint> requestpoints = new HashSet<>();

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

    public Request created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getCustomer() {
        return customer;
    }

    public Request customer(String customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public Request phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTotal() {
        return total;
    }

    public Request total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getComplete() {
        return complete;
    }

    public Request complete(String complete) {
        this.complete = complete;
        return this;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public Integer getSales() {
        return sales;
    }

    public Request sales(Integer sales) {
        this.sales = sales;
        return this;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getAddress() {
        return address;
    }

    public Request address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Requestpoint> getRequestpoints() {
        return requestpoints;
    }

    public Request requestpoints(Set<Requestpoint> requestpoints) {
        this.requestpoints = requestpoints;
        return this;
    }

    public Request addRequestpoints(Requestpoint requestpoint) {
        this.requestpoints.add(requestpoint);
        requestpoint.setRequest(this);
        return this;
    }

    public Request removeRequestpoints(Requestpoint requestpoint) {
        this.requestpoints.remove(requestpoint);
        requestpoint.setRequest(null);
        return this;
    }

    public void setRequestpoints(Set<Requestpoint> requestpoints) {
        this.requestpoints = requestpoints;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        return id != null && id.equals(((Request) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", customer='" + getCustomer() + "'" +
            ", phone='" + getPhone() + "'" +
            ", total=" + getTotal() +
            ", complete='" + getComplete() + "'" +
            ", sales=" + getSales() +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
