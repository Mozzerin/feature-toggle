package com.mmozhzhe.featuretoggle.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class FeatureToggleWeb {

    @JsonProperty(value = "display_name")
    private String displayName;

    @JsonProperty(value = "technical_name", required = true)
    @NotBlank(message = "technical name cannot be blank")
    private String technicalName;

    @JsonProperty(value = "description")
    @Size(max = 256, message = "Description is limited with 256 chars")
    private String description;

    @JsonProperty(value = "expires_on")
    private LocalDateTime expiresOn;

    @JsonProperty(value = "inverted", required = true)
    private boolean inverted;

    @JsonProperty(value = "released", required = true)
    private boolean released;

    @JsonProperty(value = "archived", required = true)
    private boolean isArchived;

    @JsonProperty(value = "version_id", required = true)
    private String versionId;

    @JsonProperty(value = "customer_ids")
    private Set<Long> customerIds;
}
