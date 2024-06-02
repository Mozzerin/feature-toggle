package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.exception.FeatureToggleServiceException;
import com.mmozhzhe.featuretoggle.model.FeatureToggleDto;

import java.util.Set;

public interface FeatureToggleServiceInterface {

    FeatureToggleDto saveNewFeatureToggle(FeatureToggleDto featureToggle) throws FeatureToggleServiceException;

    FeatureToggleDto updateFeatureToggle(FeatureToggleDto featureToggleDto) throws FeatureToggleServiceException;

    Set<FeatureToggleDto> archive(Set<String> featureToggleNames) throws FeatureToggleServiceException;
}
