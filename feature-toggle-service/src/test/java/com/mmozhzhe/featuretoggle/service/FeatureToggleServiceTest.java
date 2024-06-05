package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.dao.FeatureToggleRepository;
import com.mmozhzhe.featuretoggle.dao.entity.FeatureToggleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FeatureToggleService.class)
@TestPropertySource(properties = {"feature-toggle.maxArchiveSize=200"})
class FeatureToggleServiceTest {

    @Autowired
    FeatureToggleService featureToggleService;

    @MockBean
    FeatureToggleRepository featureToggleRepository;

    @Test
    void findById() {
        FeatureToggleEntity featureToggleEntity = new FeatureToggleEntity();
        String featureName = "technicalName";
        featureToggleEntity.setTechnicalName(featureName);
        when(featureToggleRepository.findById(any())).thenReturn(Optional.of(featureToggleEntity));

        //
        Optional<FeatureToggleEntity> featureToggle = featureToggleRepository.findById(featureName);

        //
        assertTrue(featureToggle.isPresent());
        assertEquals(featureName, featureToggle.get().getTechnicalName());
    }
}
