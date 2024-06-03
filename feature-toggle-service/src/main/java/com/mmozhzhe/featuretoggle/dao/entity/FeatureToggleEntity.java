package com.mmozhzhe.featuretoggle.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "FEATURETOGGLE", schema = "DBO", indexes = {
        @Index(name = "TECHNICALNAME_INDEX", columnList = "TECHNICALNAME")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FeatureToggleEntity {

    @Column(name = "ID", nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "DISPLAYNAME", length = 128)
    private String displayName;

    @Id
    @Column(name = "TECHNICALNAME", nullable = false, unique = true, length = 128)
    private String technicalName;

    @Column(name = "EXPIRESON")
    private LocalDateTime expiresOn;

    @Column(name = "DESCRIPTION", length = 256)
    private String description;

    @Column(name = "INVERTED", nullable = false)
    private boolean inverted;

    @Column(name = "RELEASED", nullable = false)
    private boolean released;

    @Column(name = "ARCHIVED")
    private boolean archived;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinColumn(name = "VERSIONID", referencedColumnName = "VERSIONNAME")
    private FeatureToggleReleaseEntity featureToggleRelease;

    @Column(name = "CREATEDAT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATEDAT")
    private LocalDateTime updatedAt;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "FEATURETOGGLECUSTOMER",
            schema = "DBO",
            joinColumns = @JoinColumn(name = "TECHNICALNAME"),
            inverseJoinColumns = @JoinColumn(name = "CUSTOMERID")
    )
    private Set<CustomerEntity> customers = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        id = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addCustomer(CustomerEntity customerEntity) {
        this.customers.add(customerEntity);
        customerEntity.getFeatureToggles().add(this);
    }

    public void updateCustomers(Set<CustomerEntity> customerEntities) {
        this.customers.forEach(x -> x.getFeatureToggles().remove(this));
        this.customers = customerEntities;
    }

    public void removeCustomer(CustomerEntity customerEntity) {
        this.customers.remove(customerEntity);
        customerEntity.getFeatureToggles().remove(this);
    }

    @Override public int hashCode() {
        return getClass().hashCode();
    }

    public String getId() {
        return id;
    }

    public FeatureToggleEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public FeatureToggleEntity setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public FeatureToggleEntity setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
        return this;
    }

    public LocalDateTime getExpiresOn() {
        return expiresOn;
    }

    public FeatureToggleEntity setExpiresOn(LocalDateTime expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FeatureToggleEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isInverted() {
        return inverted;
    }

    public FeatureToggleEntity setInverted(boolean inverted) {
        this.inverted = inverted;
        return this;
    }

    public boolean isReleased() {
        return released;
    }

    public FeatureToggleEntity setReleased(boolean released) {
        this.released = released;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public FeatureToggleEntity setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public FeatureToggleEntity setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Set<CustomerEntity> getCustomers() {
        return customers;
    }

    public boolean isArchived() {
        return archived;
    }

    public FeatureToggleEntity setArchived(boolean archived) {
        this.archived = archived;
        return this;
    }

    public FeatureToggleEntity setCustomers(Set<CustomerEntity> customers) {
        this.customers = customers;
        return this;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FeatureToggleEntity that = (FeatureToggleEntity) o;
        return inverted == that.inverted && released == that.released && archived == that.archived && Objects.equals(id, that.id) && Objects.equals(displayName, that.displayName)
                && Objects.equals(technicalName, that.technicalName) && Objects.equals(expiresOn, that.expiresOn) && Objects.equals(description, that.description)
                && Objects.equals(featureToggleRelease, that.featureToggleRelease) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt)
                && Objects.equals(customers, that.customers);
    }

    public FeatureToggleReleaseEntity getFeatureToggleRelease() {
        return featureToggleRelease;
    }

    public FeatureToggleEntity setFeatureToggleRelease(FeatureToggleReleaseEntity featureToggleRelease) {
        this.featureToggleRelease = featureToggleRelease;
        return this;
    }
}
