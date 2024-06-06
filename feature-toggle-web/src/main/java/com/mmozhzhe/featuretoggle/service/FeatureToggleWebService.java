package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.dto.FeatureToggleWeb;
import com.mmozhzhe.featuretoggle.dto.PaginationFeaturesWeb;
import com.mmozhzhe.featuretoggle.exception.FeatureToggleServiceException;
import com.mmozhzhe.featuretoggle.exception.FeatureToggleWebException;
import com.mmozhzhe.featuretoggle.model.FeatureToggleDto;
import com.mmozhzhe.featuretoggle.model.PaginationFeatures;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mmozhzhe.featuretoggle.exception.ErrorCodes.FEATURE_CREATION_FAILED;
import static com.mmozhzhe.featuretoggle.exception.ErrorCodes.FEATURE_UPDATE_FAILED;
import static com.mmozhzhe.featuretoggle.exception.ErrorCodes.FEATURE_VALIDATION_NAMES_FAILED;
import static com.mmozhzhe.featuretoggle.exception.ErrorCodes.INPUT_VALIDATION_FAILED;
import static com.mmozhzhe.featuretoggle.exception.ErrorCodes.NOT_FOUND;

@Service
@Slf4j
public class FeatureToggleWebService {

    private final Integer maxArchiveSize;

    private final FeatureToggleServiceInterface featureToggleServiceInterface;

    public FeatureToggleWebService(FeatureToggleServiceInterface featureToggleServiceInterface, @Value("${feature-toggle.maxArchiveSize}") Integer maxArchiveSize) {
        this.maxArchiveSize = maxArchiveSize;
        this.featureToggleServiceInterface = featureToggleServiceInterface;
    }

    public FeatureToggleWeb createNewFeatureToggle(FeatureToggleWeb featureToggle) {
        try {
            log.info("Create new feature toggle {}", featureToggle);
            FeatureToggleDto featureToggleDto = featureToggleServiceInterface.saveNewFeatureToggle(toFeatureToggleDto(featureToggle));
            return toFeatureToggleWeb(featureToggleDto);
        } catch (FeatureToggleServiceException e) {
            throw new FeatureToggleWebException(e.getMessage(), FEATURE_CREATION_FAILED, e);
        }
    }

    public FeatureToggleWeb updateFeatureToggle(String technicalName, FeatureToggleWeb featureToggle) {
        log.info("Update feature toggle with name {}", technicalName);
        if (technicalName != null && featureToggle.getTechnicalName() != null && !technicalName.equals(featureToggle.getTechnicalName())) {
            String message = "Change technical name is not possible: " + featureToggle.getTechnicalName() + " not equals " + technicalName;
            throw new FeatureToggleWebException(message, FEATURE_VALIDATION_NAMES_FAILED);
        }
        try {
            FeatureToggleDto featureToggleDto = featureToggleServiceInterface.updateFeatureToggle(toFeatureToggleDto(featureToggle));
            return toFeatureToggleWeb(featureToggleDto);
        } catch (FeatureToggleServiceException e) {
            throw new FeatureToggleWebException(e.getMessage(), FEATURE_UPDATE_FAILED, e);
        }
    }

    public Set<FeatureToggleWeb> archive(Set<String> featureToggleNames) {
        log.info("Archive feature toggles {}", featureToggleNames);
        if (featureToggleNames == null || featureToggleNames.isEmpty()) {
            throw new FeatureToggleWebException("Provide list of feature toggles to archive", INPUT_VALIDATION_FAILED);
        }
        if (featureToggleNames.size() > maxArchiveSize) {
            throw new FeatureToggleWebException("Only " + maxArchiveSize + " toggles might be archived per request", INPUT_VALIDATION_FAILED);
        }
        try {
            Set<FeatureToggleDto> toggleDtos = featureToggleServiceInterface.archive(featureToggleNames);
            return toggleDtos.stream().map(FeatureToggleWebService::toFeatureToggleWeb).collect(Collectors.toSet());
        } catch (FeatureToggleServiceException e) {
            throw new FeatureToggleWebException(e.getMessage(), FEATURE_UPDATE_FAILED, e);
        }
    }

    public PaginationFeaturesWeb findAll(int pageNo, int pageSize, String role) {
        log.info("Find all Feature toggles with pageNo : {}, pageSize : {}", pageNo, pageSize);
        PaginationFeatures page = featureToggleServiceInterface.findAll(pageNo, pageSize, role);
        List<FeatureToggleWeb> toggleWebs = page.getFeatureToggles().stream()
                .map(FeatureToggleWebService::toFeatureToggleWeb)
                .collect(Collectors.toList());
        return PaginationFeaturesWeb.builder()
                .featureToggles(toggleWebs)
                .totalCount(page.getTotalCount())
                .totalPages(page.getTotalPages())
                .build();
    }

    public FeatureToggleWeb find(String technicalName) {
        try {
            log.info("Find Feature toggle with technical name {}", technicalName);
            FeatureToggleDto featureToggleDto = null;
            featureToggleDto = featureToggleServiceInterface.findById(technicalName);
            return toFeatureToggleWeb(featureToggleDto);
        } catch (FeatureToggleServiceException e) {
            throw new FeatureToggleWebException(e.getMessage(), NOT_FOUND, e);
        }
    }

    private static FeatureToggleDto toFeatureToggleDto(FeatureToggleWeb featureToggleWeb) {
        return FeatureToggleDto.builder()
                .description(featureToggleWeb.getDescription())
                .displayName(featureToggleWeb.getDisplayName())
                .technicalName(featureToggleWeb.getTechnicalName())
                .released(featureToggleWeb.isReleased())
                .versionId(featureToggleWeb.getVersionId())
                .customerIds(featureToggleWeb.getCustomerIds())
                .expiresOn(featureToggleWeb.getExpiresOn())
                .inverted(featureToggleWeb.isInverted())
                .isArchived(featureToggleWeb.isArchived())
                .build();
    }

    private static FeatureToggleWeb toFeatureToggleWeb(FeatureToggleDto featureToggleDto) {
        return FeatureToggleWeb.builder()
                .description(featureToggleDto.getDescription())
                .displayName(featureToggleDto.getDisplayName())
                .technicalName(featureToggleDto.getTechnicalName())
                .released(featureToggleDto.isReleased())
                .versionId(featureToggleDto.getVersionId())
                .customerIds(featureToggleDto.getCustomerIds())
                .expiresOn(featureToggleDto.getExpiresOn())
                .inverted(featureToggleDto.isInverted())
                .isArchived(featureToggleDto.isArchived())
                .build();
    }
}
