package com.balbaursak.webapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Feedback.
 */
@Entity
@Table(name = "feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Feedback implements Serializable {

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
    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "vote", nullable = false)
    private Integer vote;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "feedbacks", allowSetters = true)
    private Produce produce;

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

    public Feedback created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getCustomer() {
        return customer;
    }

    public Feedback customer(String customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getVote() {
        return vote;
    }

    public Feedback vote(Integer vote) {
        this.vote = vote;
        return this;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public String getContent() {
        return content;
    }

    public Feedback content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Produce getProduce() {
        return produce;
    }

    public Feedback produce(Produce produce) {
        this.produce = produce;
        return this;
    }

    public void setProduce(Produce produce) {
        this.produce = produce;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feedback)) {
            return false;
        }
        return id != null && id.equals(((Feedback) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Feedback{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", customer='" + getCustomer() + "'" +
            ", vote=" + getVote() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
