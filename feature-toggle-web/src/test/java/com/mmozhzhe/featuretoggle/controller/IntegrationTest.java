package com.mmozhzhe.featuretoggle.controller;

import com.mmozhzhe.featuretoggle.FeatureToggleWebApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = FeatureToggleWebApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IntegrationTest {

    String createFeatureRequest =
            "{\"display_name\": \"Example Display Name\",\"technical_name\": \"%s\",\"description\": \"This is an example description.\",\"expires_on\": \"2024-12-31T23:59:59\",\"inverted\": true,\"customer_ids\": [12,13]}";

    String releaseRequest = "{\"version_id\":\"%s\",\"description\":\"best version\",\"feature_toggle_names\":[\"%s\"]}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllFeatures() throws Exception {

        String featureName = UUID.randomUUID().toString();
        mockMvc.perform(post("/api/v1/features")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(createFeatureRequest, featureName)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/features?pageNo=0&pageSize=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.feature_toggles[0].display_name").value("Example Display Name"));
    }

    @Test
    void releaseTest() throws Exception {

        String featureNameToRelease = UUID.randomUUID().toString();
        String version = UUID.randomUUID().toString();

        mockMvc.perform(
                        post("/api/v1/features")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.format(createFeatureRequest, featureNameToRelease))
                )
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/operations/features/release")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(releaseRequest, version, featureNameToRelease))
                )
                .andDo(print())
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/api/v1/operations/features/release")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(releaseRequest, version, featureNameToRelease))
                        .header("Role", "admin")
                )
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/features/" + featureNameToRelease)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.technical_name").value(featureNameToRelease))
                .andExpect(MockMvcResultMatchers.jsonPath("$.released").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.version_id").value(version));
    }
}

