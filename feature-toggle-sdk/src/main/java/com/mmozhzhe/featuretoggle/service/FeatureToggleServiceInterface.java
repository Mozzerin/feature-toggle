package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.exception.FeatureToggleServiceException;
import com.mmozhzhe.featuretoggle.model.FeatureToggleDto;
import com.mmozhzhe.featuretoggle.model.PaginationFeatures;

import java.util.Set;

public interface FeatureToggleServiceInterface {

    FeatureToggleDto saveNewFeatureToggle(FeatureToggleDto featureToggle) throws FeatureToggleServiceException;

    FeatureToggleDto updateFeatureToggle(FeatureToggleDto featureToggleDto) throws FeatureToggleServiceException;

    Set<FeatureToggleDto> archive(Set<String> featureToggleNames) throws FeatureToggleServiceException;

    PaginationFeatures findAll(int pageNo, int pageSize, String role);

    FeatureToggleDto findById(String technicalName) throws FeatureToggleServiceException;
}
