package com.mmozhzhe.featuretoggle.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponseDtoBody {

    @JsonProperty(value = "name", required = true)
    private String featureToggleName;

    @JsonProperty(value = "active", required = true)
    private Boolean active;

    @JsonProperty(value = "inverted", required = true)
    private Boolean inverted;

    @JsonProperty(value = "expired", required = true)
    private Boolean expired;

    @JsonProperty(value = "releaseVersion")
    private String releaseVersion;

}
