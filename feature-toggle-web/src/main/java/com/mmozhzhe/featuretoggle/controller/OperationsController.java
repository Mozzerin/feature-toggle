package com.mmozhzhe.featuretoggle.controller;

import com.mmozhzhe.featuretoggle.dto.FeatureToggleWeb;
import com.mmozhzhe.featuretoggle.dto.ReleaseWeb;
import com.mmozhzhe.featuretoggle.dto.SearchRequestWeb;
import com.mmozhzhe.featuretoggle.dto.SearchResponseWeb;
import com.mmozhzhe.featuretoggle.service.CustomerWebService;
import com.mmozhzhe.featuretoggle.service.FeatureToggleWebService;
import com.mmozhzhe.featuretoggle.service.ReleaseToggleWebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/operations/features")
@Slf4j
public class OperationsController {

    private final FeatureToggleWebService featureToggleService;
    private final CustomerWebService customerWebService;
    private final ReleaseToggleWebService releaseWebService;

    @PutMapping(path = "/archive")
    public ResponseEntity<FeatureToggleWeb> archive(@RequestBody Set<String> featureToggleNames) {
        log.info("Requested to archive those feature flags: {}", featureToggleNames);
        Set<FeatureToggleWeb> archivedList = featureToggleService.archive(featureToggleNames);
        return new ResponseEntity(archivedList, HttpStatus.OK);
    }

    @PutMapping(path = "/release")
    public ResponseEntity<ReleaseWeb> release(@RequestBody ReleaseWeb releaseWeb) {
        log.info("New release requested: {}", releaseWeb.getVersionId());
        ReleaseWeb releaseList = releaseWebService.release(releaseWeb);
        return new ResponseEntity(releaseList, HttpStatus.CREATED);
    }

    @PostMapping(path = "/release/search")
    public ResponseEntity<Set<ReleaseWeb>> findAll(Set<String> technicalNames) {
        log.info("Get release versions for toggles: {}", technicalNames);
        Set<ReleaseWeb> releaseList = releaseWebService.getAllReleasesForName(technicalNames);
        return new ResponseEntity(releaseList, HttpStatus.CREATED);
    }
}
