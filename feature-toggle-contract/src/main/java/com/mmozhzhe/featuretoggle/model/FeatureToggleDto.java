package com.mmozhzhe.featuretoggle.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureToggleDto {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "display_name")
    private String displayName;

    @JsonProperty(value = "technical_name", required = true)
    private String technicalName;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "expires_on")
    private LocalDateTime expiresOn;

    @JsonProperty(value = "inverted", required = true)
    private boolean inverted;

    @JsonProperty(value = "released")
    private boolean released;

    @JsonProperty(value = "archived")
    private boolean isArchived;

    @JsonProperty(value = "version_id")
    private String versionId;

    @JsonProperty(value = "customer_ids")
    private Set<Long> customerIds;
}
