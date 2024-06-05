package com.mmozhzhe.featuretoggle.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FEATURETOGGLERELEASE", schema = "DBO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeatureToggleReleaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, length = 36)
    private String versionid;

    @Column(name = "VERSIONNAME", nullable = false, length = 36)
    private String versionName;

    @Column(name = "DESCRIPTION", length = 256)
    private String description;

    @OneToMany
    @JoinColumn(name = "VERSIONID", referencedColumnName = "VERSIONNAME")
    @Builder.Default
    private Set<FeatureToggleEntity> featureToggles = new HashSet<>();

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATEDAT", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
