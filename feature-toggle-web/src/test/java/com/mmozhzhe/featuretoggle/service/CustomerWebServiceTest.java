package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.dto.SearchFeature;
import com.mmozhzhe.featuretoggle.dto.SearchRequestWeb;
import com.mmozhzhe.featuretoggle.dto.SearchRequestWebBody;
import com.mmozhzhe.featuretoggle.dto.SearchResponseWeb;
import com.mmozhzhe.featuretoggle.dto.SearchResponseWebBody;
import com.mmozhzhe.featuretoggle.exception.CustomerServiceException;
import com.mmozhzhe.featuretoggle.model.SearchResponseDto;
import com.mmozhzhe.featuretoggle.model.SearchResponseDtoBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerWebService.class)
@TestPropertySource(properties = {"feature-toggle.maxFeatureRequestSize=200"})
class CustomerWebServiceTest {

    @MockBean
    CustomerServiceInterface mock;

    @Autowired
    CustomerWebService customerWebService;

    @Test
    void search() throws CustomerServiceException {
        SearchFeature feature = SearchFeature.builder().featureName("featureName").build();
        SearchRequestWebBody body = SearchRequestWebBody.builder().customerId("12").features(Set.of(feature)).build();
        SearchRequestWeb requestWeb = SearchRequestWeb.builder().featureRequest(body).build();

        SearchResponseDtoBody featureName = SearchResponseDtoBody.builder().featureToggleName("featureName").expired(false).build();
        SearchResponseDto features = SearchResponseDto.builder().features(Set.of(featureName)).build();
        when(mock.search(any())).thenReturn(features);

        //then
        SearchResponseWeb result = customerWebService.search(requestWeb);

        //when
        assertEquals(1, result.getFeatures().size());
    }
}
