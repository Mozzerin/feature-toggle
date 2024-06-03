package com.mmozhzhe.featuretoggle.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "CUSTOMER", schema = "DBO")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    @Column(name = "ID", nullable = false, updatable = false, length = 36)
    private String id;

    @Id
    @Column(name = "CUSTOMERID", nullable = false)
    private Long customerId;

    @Column(name = "CREATEDAT", nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "customers", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<FeatureToggleEntity> featureToggles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        id = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        createdAt = LocalDateTime.now();
    }

    public void addFeatureToggle(FeatureToggleEntity featureToggleEntity) {
        this.featureToggles.add(featureToggleEntity);
        featureToggleEntity.getCustomers().add(this);
    }

    public void removeFeatureToggle(FeatureToggleEntity featureToggleEntity) {
        this.featureToggles.remove(featureToggleEntity);
        featureToggleEntity.getCustomers().remove(this);
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(customerId, that.customerId) && Objects.equals(createdAt, that.createdAt) && Objects.equals(
                featureToggles, that.featureToggles);
    }

    @Override public int hashCode() {
        return getClass().hashCode();
    }

    public String getId() {
        return id;
    }

    public CustomerEntity setId(String id) {
        this.id = id;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public CustomerEntity setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public CustomerEntity setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Set<FeatureToggleEntity> getFeatureToggles() {
        return featureToggles;
    }
}
