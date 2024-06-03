package com.mmozhzhe.featuretoggle.dao;

import com.mmozhzhe.featuretoggle.dao.entity.FeatureToggleReleaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseToggleRepository extends JpaRepository<FeatureToggleReleaseEntity, String> {

}
