package com.mmozhzhe.featuretoggle.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "FEATURETOGGLERELEASE", schema = "DBO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeatureToggleReleaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VERSIONID", nullable = false, length = 36)
    private String versionid;

    @Column(name = "VERSION", nullable = false, length = 128)
    private String version;

    @Column(name = "DESCRIPTION", length = 256)
    private String description;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATEDAT", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
