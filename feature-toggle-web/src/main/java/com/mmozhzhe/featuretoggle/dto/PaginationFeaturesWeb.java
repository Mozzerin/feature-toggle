package com.mmozhzhe.featuretoggle.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmozhzhe.featuretoggle.model.FeatureToggleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationFeaturesWeb {

    @JsonProperty(value = "feature_toggles")
    private List<FeatureToggleWeb> featureToggles;
    @JsonProperty(value = "total_count")
    private long totalCount;
    @JsonProperty(value = "total_pages")
    private int totalPages;

}
