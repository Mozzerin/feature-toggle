package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.dao.FeatureToggleRepository;
import com.mmozhzhe.featuretoggle.dao.ReleaseToggleRepository;
import com.mmozhzhe.featuretoggle.dao.entity.FeatureToggleEntity;
import com.mmozhzhe.featuretoggle.dao.entity.FeatureToggleReleaseEntity;
import com.mmozhzhe.featuretoggle.exception.FeatureToggleServiceException;
import com.mmozhzhe.featuretoggle.exception.ReleaseToggleServiceException;
import com.mmozhzhe.featuretoggle.model.FeatureToggleDto;
import com.mmozhzhe.featuretoggle.model.ReleaseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReleaseToggleService implements ReleaseToggleServiceInterface {

    @Value("${feature-toggle.maxReleaseSize}")
    private Integer maxReleaseSize;

    private final ReleaseToggleRepository releaseToggleRepository;
    private final FeatureToggleRepository featureToggleRepository;

    public ReleaseToggleService(ReleaseToggleRepository releaseToggleRepository, FeatureToggleRepository featureToggleRepository) {
        this.releaseToggleRepository = releaseToggleRepository;
        this.featureToggleRepository = featureToggleRepository;
    }

    @Override
    @Transactional
    public ReleaseDto release(ReleaseDto releaseDto) throws ReleaseToggleServiceException {
        if (releaseDto.getVersionId() == null || releaseDto.getFeatureToggleNames().isEmpty()) {
            throw new ReleaseToggleServiceException("Version and Feature Toggle Name cannot be empty");
        }
        if (releaseDto.getFeatureToggleNames().size() > maxReleaseSize) {
            throw new ReleaseToggleServiceException("Only " + maxReleaseSize + " toggles are into single release");
        }
        try {
            Set<FeatureToggleEntity> toggleEntities = featureToggleRepository.findByTechnicalNameIn(releaseDto.getFeatureToggleNames());
            log.info("Releasing : [{}] feature toggles, all archived will be filtered", toggleEntities.size());
            if (toggleEntities.isEmpty()) {
                throw new ReleaseToggleServiceException("Feature Toggles cannot be empty");
            }
            FeatureToggleReleaseEntity releaseEntity = FeatureToggleReleaseEntity.builder()
                    .featureToggles(toggleEntities)
                    .description(releaseDto.getDescription())
                    .versionName(releaseDto.getVersionId())
                    .build();
            Set<FeatureToggleEntity> entities = toggleEntities.stream()
                    .filter(featureToggleEntity -> !featureToggleEntity.isArchived())
                    .map(featureToggleEntity -> featureToggleEntity.setReleased(true))
                    .map(featureToggleEntity -> featureToggleEntity.setFeatureToggleRelease(releaseEntity))
                    .collect(Collectors.toSet());
            featureToggleRepository.saveAll(entities);
            return releaseDto;
        } catch (Exception e) {
            throw new ReleaseToggleServiceException("Update for new feature toggle failed", e);
        }
    }

    @Override
    @Transactional
    public Set<ReleaseDto> getAllReleases() throws ReleaseToggleServiceException {
        return Set.of();
    }
}


