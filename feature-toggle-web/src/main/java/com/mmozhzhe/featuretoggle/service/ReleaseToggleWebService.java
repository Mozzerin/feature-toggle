package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.dao.entity.FeatureToggleEntity;
import com.mmozhzhe.featuretoggle.dto.ReleaseWeb;
import com.mmozhzhe.featuretoggle.exception.FeatureToggleServiceException;
import com.mmozhzhe.featuretoggle.exception.FeatureToggleWebException;
import com.mmozhzhe.featuretoggle.exception.ReleaseToggleServiceException;
import com.mmozhzhe.featuretoggle.model.ReleaseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mmozhzhe.featuretoggle.exception.ErrorCodes.RELEASE_FAILED;

@Service
@Slf4j
public class ReleaseToggleWebService {

    private final Integer maxReleaseSize;

    private final ReleaseToggleServiceInterface releaseToggleServiceInterface;

    public ReleaseToggleWebService(ReleaseToggleServiceInterface releaseToggleServiceInterface, @Value("${feature-toggle.maxReleaseSize}") Integer maxReleaseSize) {
        this.maxReleaseSize = maxReleaseSize;
        this.releaseToggleServiceInterface = releaseToggleServiceInterface;
    }

    public ReleaseWeb release(ReleaseWeb releaseWeb) {
        log.info("Release feature toggles {}", releaseWeb.getFeatureToggleNames());
        if (releaseWeb.getVersionId() == null || releaseWeb.getFeatureToggleNames().isEmpty()) {
            throw new FeatureToggleWebException("Version is null or no feature toggle names provided", RELEASE_FAILED);
        }
        if (releaseWeb.getFeatureToggleNames().size() > maxReleaseSize) {
            throw new FeatureToggleWebException("Only " + maxReleaseSize + " toggles are into single release", RELEASE_FAILED);
        }
        try {
            ReleaseDto release = releaseToggleServiceInterface.release(toReleaseDto(releaseWeb));
            return toReleaseWeb(release);
        } catch (ReleaseToggleServiceException e) {
            throw new FeatureToggleWebException(e.getMessage(), RELEASE_FAILED, e);
        }
    }

    public Set<ReleaseWeb> getAllReleasesForName(Set<String> technicalNames) {
        return null;
    }

    private ReleaseWeb toReleaseWeb(ReleaseDto release) {
        return ReleaseWeb.builder()
                .description(release.getDescription())
                .versionId(release.getVersionId())
                .description(release.getDescription())
                .featureToggleNames(release.getFeatureToggleNames())
                .build();
    }

    private ReleaseDto toReleaseDto(ReleaseWeb releaseWeb) {
        return ReleaseDto.builder()
                .description(releaseWeb.getDescription())
                .versionId(releaseWeb.getVersionId())
                .description(releaseWeb.getDescription())
                .featureToggleNames(releaseWeb.getFeatureToggleNames())
                .build();
    }
}
