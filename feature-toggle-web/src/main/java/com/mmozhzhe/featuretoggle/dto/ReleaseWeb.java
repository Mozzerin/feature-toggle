package com.mmozhzhe.featuretoggle.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseWeb {

    @JsonProperty(value = "version_id", required = true)
    private String versionId;

    @JsonProperty(value = "description", required = true)
    private String description;

    @JsonProperty(value = "feature_toggle_names")
    private Set<String> featureToggleNames;
}
