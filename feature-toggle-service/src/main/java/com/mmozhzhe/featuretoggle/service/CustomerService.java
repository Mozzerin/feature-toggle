package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.dao.CustomerRepository;
import com.mmozhzhe.featuretoggle.dao.entity.CustomerEntity;
import com.mmozhzhe.featuretoggle.exception.CustomerServiceException;
import com.mmozhzhe.featuretoggle.model.SearchRequestDto;
import com.mmozhzhe.featuretoggle.model.SearchRequestDtoBody;
import com.mmozhzhe.featuretoggle.model.SearchResponseDto;
import com.mmozhzhe.featuretoggle.model.SearchResponseDtoBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService implements CustomerServiceInterface {

    @Value("${feature-toggle.maxFeatureRequestSize}")
    private Integer maxFeatureRequestSize;

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override public SearchResponseDto search(SearchRequestDto searchRequestDto) throws CustomerServiceException {
        validate(searchRequestDto);
        SearchRequestDtoBody featureRequest = searchRequestDto.getFeatureRequest();
        Set<String> set = featureRequest.getFeatures().stream().map(x -> x.getFeatureName()).collect(Collectors.toSet());
        String customerId = featureRequest.getCustomerId();
        CustomerEntity customer = customerRepository.findByCustomerIdAndFeatureToggles_TechnicalNameIn(Long.valueOf(customerId), set)
                .orElseThrow(() -> new CustomerServiceException("Customer " + customerId + " not found"));
        return toSearchResponseDto(customer);
    }

    private SearchResponseDto toSearchResponseDto(CustomerEntity customer) {
        Set<SearchResponseDtoBody> set = customer.getFeatureToggles()
                .stream()
                .map(x -> SearchResponseDtoBody.builder()
                        .active(x.isReleased())
                        .expired(LocalDateTime.now().isAfter(x.getExpiresOn()))
                        .inverted(x.isInverted())
                        .releaseVersion(x.getFeatureToggleRelease() != null ? x.getFeatureToggleRelease().getVersionName() : null)
                        .featureToggleName(x.getDisplayName())
                        .build())
                .collect(Collectors.toSet());
        return SearchResponseDto.builder().features(set).build();
    }

    private void validate(SearchRequestDto searchRequestDto) throws CustomerServiceException {
        if (searchRequestDto == null
                || searchRequestDto.getFeatureRequest() == null
                || searchRequestDto.getFeatureRequest().getCustomerId() == null
        ) {
            throw new CustomerServiceException("Invalid search request");
        }
        if (searchRequestDto.getFeatureRequest().getFeatures().size() > maxFeatureRequestSize) {
            throw new CustomerServiceException("You requested more then " + maxFeatureRequestSize + " allowed features size per customer");
        }
    }
}


