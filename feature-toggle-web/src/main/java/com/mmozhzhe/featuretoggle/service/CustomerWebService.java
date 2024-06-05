package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.dto.SearchRequestWeb;
import com.mmozhzhe.featuretoggle.dto.SearchResponseWeb;
import com.mmozhzhe.featuretoggle.dto.SearchResponseWebBody;
import com.mmozhzhe.featuretoggle.exception.CustomerServiceException;
import com.mmozhzhe.featuretoggle.exception.FeatureToggleWebException;
import com.mmozhzhe.featuretoggle.model.SearchFeatureDto;
import com.mmozhzhe.featuretoggle.model.SearchRequestDto;
import com.mmozhzhe.featuretoggle.model.SearchRequestDtoBody;
import com.mmozhzhe.featuretoggle.model.SearchResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.mmozhzhe.featuretoggle.exception.ErrorCodes.INVALID_SEARCH_REQUEST;
import static com.mmozhzhe.featuretoggle.exception.ErrorCodes.SEARCH_REQUEST_FAILED;

@Service
@Slf4j
public class CustomerWebService {

    private final Integer maxFeatureRequestSize;

    private final CustomerServiceInterface customerServiceInterface;

    public CustomerWebService(CustomerServiceInterface customerServiceInterface, @Value("${feature-toggle.maxFeatureRequestSize}") Integer maxFeatureRequestSize) {
        this.maxFeatureRequestSize = maxFeatureRequestSize;
        this.customerServiceInterface = customerServiceInterface;
    }

    public SearchResponseWeb search(SearchRequestWeb searchRequestWeb) {
        try {
            validate(searchRequestWeb);
            log.info("Search {}", searchRequestWeb);
            SearchResponseDto searchResult = customerServiceInterface.search(toSearchRequestDto(searchRequestWeb));
            return teSearchResponseWeb(searchResult);
        } catch (CustomerServiceException e) {
            throw new FeatureToggleWebException(e.getMessage(), SEARCH_REQUEST_FAILED, e);
        }
    }

    private SearchResponseWeb teSearchResponseWeb(SearchResponseDto searchResult) {
        Set<SearchResponseWebBody> set = searchResult.getFeatures().stream()
                .map(x -> SearchResponseWebBody.builder()
                        .expired(x.getExpired())
                        .featureToggleName(x.getFeatureToggleName())
                        .active(x.getActive())
                        .releaseVersion(x.getReleaseVersion())
                        .inverted(x.getInverted())
                        .build()
                ).collect(Collectors.toSet());

        return SearchResponseWeb.builder().features(set).build();
    }

    private SearchRequestDto toSearchRequestDto(SearchRequestWeb searchRequestWeb) {
        Set<SearchFeatureDto> featureDtos = searchRequestWeb.getFeatureRequest().getFeatures().stream()
                .map(x -> SearchFeatureDto.builder().featureName(x.getFeatureName()).build())
                .collect(Collectors.toSet());
        SearchRequestDtoBody requestDtoBody = SearchRequestDtoBody.builder()
                .customerId(searchRequestWeb.getFeatureRequest().getCustomerId())
                .features(featureDtos)
                .build();
        return SearchRequestDto.builder()
                .featureRequest(requestDtoBody)
                .build();
    }

    private void validate(SearchRequestWeb searchRequestWeb) {
        if (searchRequestWeb == null
                || searchRequestWeb.getFeatureRequest() == null
                || searchRequestWeb.getFeatureRequest().getCustomerId() == null
        ) {
            throw new FeatureToggleWebException("Invalid search request", INVALID_SEARCH_REQUEST);
        }
        if (searchRequestWeb.getFeatureRequest().getFeatures().size() > maxFeatureRequestSize) {
            throw new FeatureToggleWebException("You requested more then max allowed features size per customer", INVALID_SEARCH_REQUEST);
        }
    }
}
