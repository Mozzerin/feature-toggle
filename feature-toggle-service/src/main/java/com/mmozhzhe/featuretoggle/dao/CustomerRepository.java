package com.mmozhzhe.featuretoggle.dao;

import com.mmozhzhe.featuretoggle.dao.entity.CustomerEntity;
import com.mmozhzhe.featuretoggle.dao.entity.FeatureToggleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

}
