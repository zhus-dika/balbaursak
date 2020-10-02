package com.balbaursak.webapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Produce.
 */
@Entity
@Table(name = "produce")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Produce implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;


    @Lob
    @Column(name = "contains", nullable = false)
    private String contains;

    @NotNull
    @Min(value = 0)
    @Column(name = "days", nullable = false)
    private Integer days;


    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Min(value = 0)
    @Column(name = "price", nullable = false)
    private Integer price;


    @Lob
    @Column(name = "file", nullable = false)
    private byte[] file;

    @Column(name = "file_content_type", nullable = false)
    private String fileContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "produces", allowSetters = true)
    private Category category;

    @OneToMany(mappedBy = "produce")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Feedback> feedbacks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Produce name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public Produce unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getContains() {
        return contains;
    }

    public Produce contains(String contains) {
        this.contains = contains;
        return this;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public Integer getDays() {
        return days;
    }

    public Produce days(Integer days) {
        this.days = days;
        return this;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getDescription() {
        return description;
    }

    public Produce description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public Produce price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public byte[] getFile() {
        return file;
    }

    public Produce file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public Produce fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Category getCategory() {
        return category;
    }

    public Produce category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public Produce feedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        return this;
    }

    public Produce addFeedbacks(Feedback feedback) {
        this.feedbacks.add(feedback);
        feedback.setProduce(this);
        return this;
    }

    public Produce removeFeedbacks(Feedback feedback) {
        this.feedbacks.remove(feedback);
        feedback.setProduce(null);
        return this;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produce)) {
            return false;
        }
        return id != null && id.equals(((Produce) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produce{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", unit='" + getUnit() + "'" +
            ", contains='" + getContains() + "'" +
            ", days=" + getDays() +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
