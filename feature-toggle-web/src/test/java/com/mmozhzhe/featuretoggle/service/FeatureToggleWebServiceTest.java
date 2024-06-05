package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.dto.FeatureToggleWeb;
import com.mmozhzhe.featuretoggle.exception.FeatureToggleServiceException;
import com.mmozhzhe.featuretoggle.model.FeatureToggleDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FeatureToggleWebService.class)
@TestPropertySource(properties = {"feature-toggle.maxArchiveSize=200"})
class FeatureToggleWebServiceTest {

    @MockBean
    FeatureToggleServiceInterface mock;

    @Autowired
    FeatureToggleWebService featureToggleWebService;

    @Test
    void createNewFeatureToggle() throws FeatureToggleServiceException {
        FeatureToggleWeb featureToggle = FeatureToggleWeb
                .builder()
                .technicalName("technicalName")
                .customerIds(Set.of(12L, 13L))
                .build();
        FeatureToggleDto result = FeatureToggleDto.builder()
                .technicalName("technicalName")
                .customerIds(Set.of(12L, 13L))
                .build();
        when(mock.saveNewFeatureToggle(any())).thenReturn(result);
        FeatureToggleWeb newFeatureToggle = featureToggleWebService.createNewFeatureToggle(featureToggle);
        assertEquals(featureToggle.getTechnicalName(), newFeatureToggle.getTechnicalName());
    }

    @Test
    void updateNewFeatureToggle() throws FeatureToggleServiceException {
        FeatureToggleWeb featureToggle = FeatureToggleWeb
                .builder()
                .technicalName("technicalName")
                .customerIds(Set.of(12L, 13L))
                .build();
        FeatureToggleDto result = FeatureToggleDto.builder()
                .technicalName("technicalName")
                .customerIds(Set.of(12L, 13L))
                .build();
        when(mock.updateFeatureToggle(any())).thenReturn(result);
        FeatureToggleWeb newFeatureToggle = featureToggleWebService.updateFeatureToggle("technicalName", featureToggle);
        assertEquals(featureToggle.getTechnicalName(), newFeatureToggle.getTechnicalName());
    }

}
