package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.dao.FeatureToggleRepository;
import com.mmozhzhe.featuretoggle.dao.entity.CustomerEntity;
import com.mmozhzhe.featuretoggle.dao.entity.FeatureToggleEntity;
import com.mmozhzhe.featuretoggle.exception.FeatureToggleServiceException;
import com.mmozhzhe.featuretoggle.model.FeatureToggleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FeatureToggleService implements FeatureToggleServiceInterface {

    @Value("${feature-toggle.maxArchiveSize}")
    private Integer maxArchiveSize;

    private final FeatureToggleRepository featureToggleRepository;

    public FeatureToggleService(FeatureToggleRepository featureToggleRepository) {
        this.featureToggleRepository = featureToggleRepository;
    }

    @Override
    @Transactional
    public FeatureToggleDto saveNewFeatureToggle(FeatureToggleDto featureToggle) throws FeatureToggleServiceException {
        validate(featureToggle);
        try {
            log.info("Save new feature flag with technical name: [{}]", featureToggle.getTechnicalName());
            FeatureToggleEntity featureToggleEntity = toFeatureToggleEntity(featureToggle);
            FeatureToggleEntity saved = featureToggleRepository.save(featureToggleEntity);
            return toFeatureToggleDto(saved);
        } catch (Exception e) {
            throw new FeatureToggleServiceException("Save for new feature toggle failed", e);
        }
    }

    @Override public FeatureToggleDto updateFeatureToggle(FeatureToggleDto featureToggle) throws FeatureToggleServiceException {
        validate(featureToggle);
        try {
            FeatureToggleEntity featureToggleEntity = featureToggleRepository.findByTechnicalName(featureToggle.getTechnicalName())
                    .orElseThrow(() -> new FeatureToggleServiceException("FeatureToggle with name not found"));
            log.info("Update feature flag with technical name: [{}]", featureToggle.getTechnicalName());
            mergeToToggleEntity(featureToggleEntity, featureToggle);
            FeatureToggleEntity saved = featureToggleRepository.save(featureToggleEntity);
            return toFeatureToggleDto(saved);
        } catch (Exception e) {
            throw new FeatureToggleServiceException("Update for new feature toggle failed", e);
        }
    }

    @Override public Set<FeatureToggleDto> archive(Set<String> featureToggleNames) throws FeatureToggleServiceException {
        if (featureToggleNames == null || featureToggleNames.isEmpty()) {
            throw new FeatureToggleServiceException("List is empty");
        }
        if (featureToggleNames.size() > maxArchiveSize) {
            throw new FeatureToggleServiceException("Only " + maxArchiveSize + " toggles are supported per request now");
        }
        try {
            Set<FeatureToggleEntity> toggleEntities = featureToggleRepository.findByTechnicalNameIn(featureToggleNames);
            log.info("Archiving : [{}] feature toggles", toggleEntities.size());
            toggleEntities.forEach(featureToggleEntity -> featureToggleEntity.setArchived(true));
            List<FeatureToggleEntity> featureToggleEntities = featureToggleRepository.saveAll(toggleEntities);
            return featureToggleEntities.stream().map(FeatureToggleService::toFeatureToggleDto).collect(Collectors.toSet());
        } catch (Exception e) {
            throw new FeatureToggleServiceException("Update for new feature toggle failed", e);
        }
    }

    private static void mergeToToggleEntity(FeatureToggleEntity featureToggleEntity, FeatureToggleDto featureToggle) {
        Set<CustomerEntity> customerEntities = featureToggle.getCustomerIds()
                .stream()
                .map(x -> new CustomerEntity()
                        .setCustomerId(x)
                )
                .collect(Collectors.toSet());

        featureToggleEntity
                .setDescription(featureToggle.getDescription())
                .setDisplayName(featureToggle.getDisplayName())
                .setTechnicalName(featureToggle.getTechnicalName() == null ? featureToggle.getDisplayName() : featureToggle.getTechnicalName())
                .setReleased(featureToggle.isReleased())
                .setVersionId(featureToggle.getVersionId())
                .setExpiresOn(featureToggle.getExpiresOn())
                .setInverted(featureToggle.isInverted());

        featureToggleEntity.updateCustomers(customerEntities);
    }

    private static FeatureToggleEntity toFeatureToggleEntity(FeatureToggleDto featureToggle) {
        Set<CustomerEntity> customerEntities = featureToggle.getCustomerIds()
                .stream()
                .map(x -> new CustomerEntity()
                        .setCustomerId(x)
                )
                .collect(Collectors.toSet());

        FeatureToggleEntity featureToggleEntity = new FeatureToggleEntity()
                .setDescription(featureToggle.getDescription())
                .setDisplayName(featureToggle.getDisplayName())
                .setTechnicalName(featureToggle.getTechnicalName() == null ? featureToggle.getDisplayName() : featureToggle.getTechnicalName())
                .setReleased(featureToggle.isReleased())
                .setVersionId(featureToggle.getVersionId())
                .setExpiresOn(featureToggle.getExpiresOn())
                .setInverted(featureToggle.isInverted());

        customerEntities.forEach(featureToggleEntity::addCustomer);

        return featureToggleEntity;
    }

    private static FeatureToggleDto toFeatureToggleDto(FeatureToggleEntity featureToggleEntity) {
        FeatureToggleDto.FeatureToggleDtoBuilder featureToggleDtoBuilder = FeatureToggleDto.builder()
                .id(featureToggleEntity.getId())
                .description(featureToggleEntity.getDescription())
                .displayName(featureToggleEntity.getDisplayName())
                .technicalName(featureToggleEntity.getTechnicalName())
                .released(featureToggleEntity.isReleased())
                .versionId(featureToggleEntity.getVersionId())
                .expiresOn(featureToggleEntity.getExpiresOn())
                .inverted(featureToggleEntity.isInverted())
                .isArchived(featureToggleEntity.isArchived());
        if (featureToggleEntity.getCustomers() != null && !featureToggleEntity.getCustomers().isEmpty()) {
            Set<Long> customerIds = featureToggleEntity.getCustomers().stream()
                    .map(x -> x.getCustomerId())
                    .collect(Collectors.toSet());
            featureToggleDtoBuilder.customerIds(customerIds);
        }
        return featureToggleDtoBuilder
                .build();
    }

    private void validate(FeatureToggleDto featureToggle) throws FeatureToggleServiceException {
        if (featureToggle.getTechnicalName() == null || featureToggle.getTechnicalName().isEmpty()) {
            throw new FeatureToggleServiceException("Technical name is required field to create new feature toggle");
        }
    }
}
