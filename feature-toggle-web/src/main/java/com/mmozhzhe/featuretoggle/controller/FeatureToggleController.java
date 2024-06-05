package com.mmozhzhe.featuretoggle.controller;

import com.mmozhzhe.featuretoggle.dto.FeatureToggleWeb;
import com.mmozhzhe.featuretoggle.dto.PaginationFeaturesWeb;
import com.mmozhzhe.featuretoggle.dto.SearchRequestWeb;
import com.mmozhzhe.featuretoggle.dto.SearchResponseWeb;
import com.mmozhzhe.featuretoggle.service.CustomerWebService;
import com.mmozhzhe.featuretoggle.service.FeatureToggleWebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/features")
@Slf4j
public class FeatureToggleController {

    private final FeatureToggleWebService featureToggleService;
    private final CustomerWebService customerWebService;

    @PostMapping
    public ResponseEntity<FeatureToggleWeb> saveFeatureToggle(@RequestBody FeatureToggleWeb featureToggle) {
        log.info("New feature toggle save request received for feature toggle with name: {}", featureToggle.getTechnicalName());
        FeatureToggleWeb newFeatureToggle = featureToggleService.createNewFeatureToggle(featureToggle);
        return new ResponseEntity(newFeatureToggle, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{technicalName}")
    public ResponseEntity<FeatureToggleWeb> updateFeatureToggle(@PathVariable(value = "technicalName") String technicalName, @RequestBody FeatureToggleWeb featureToggle) {
        log.info("Update request received for feature toggle with name: {}", technicalName);
        FeatureToggleWeb newFeatureToggle = featureToggleService.updateFeatureToggle(technicalName, featureToggle);
        return new ResponseEntity(newFeatureToggle, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PaginationFeaturesWeb> findAllFeatures(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestHeader(name = "Role", required = false) String role) {
        log.info("Find all features");
        PaginationFeaturesWeb result = featureToggleService.findAll(pageNo, pageSize, role);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/{technicalName}")
    public ResponseEntity<FeatureToggleWeb> findById(@PathVariable(value = "technicalName") String technicalName) {
        log.info("Find feature by id {}", technicalName);
        FeatureToggleWeb result = featureToggleService.find(technicalName);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponseWeb> search(@RequestBody SearchRequestWeb searchRequest) {
        log.info("Search features for customer: {}", searchRequest);
        SearchResponseWeb result = customerWebService.search(searchRequest);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @PutMapping(path = "/archive")
    public ResponseEntity<FeatureToggleWeb> archive(@RequestBody Set<String> featureToggleNames) {
        log.info("Requested to archive those feature flags: {}", featureToggleNames);
        Set<FeatureToggleWeb> archivedList = featureToggleService.archive(featureToggleNames);
        return new ResponseEntity(archivedList, HttpStatus.OK);
    }
}
