package com.mmozhzhe.featuretoggle.dao;

import com.mmozhzhe.featuretoggle.dao.entity.FeatureToggleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FeatureToggleRepository extends JpaRepository<FeatureToggleEntity, String> {

    Optional<FeatureToggleEntity> findByTechnicalName(String technicalId);

    Set<FeatureToggleEntity> findByTechnicalNameIn(Set<String> technicalNames);

}
