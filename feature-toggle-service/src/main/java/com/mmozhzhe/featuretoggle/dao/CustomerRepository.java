package com.mmozhzhe.featuretoggle.dao;

import com.mmozhzhe.featuretoggle.dao.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByCustomerIdAndFeatureToggles_TechnicalNameIn(Long s, Set<String> technicalNames);

    @Query("select customer from CustomerEntity customer "
            + "left join customer.featureToggles featureToggle "
            + "where featureToggle.technicalName in (:technicalNames) and customer.customerId = :id")
    Optional<CustomerEntity> findCustomerById(Long id, Set<String> technicalNames);

    Optional<CustomerEntity> findByCustomerId(Long s);
}
