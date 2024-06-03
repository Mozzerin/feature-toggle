package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.exception.ReleaseToggleServiceException;
import com.mmozhzhe.featuretoggle.model.FeatureToggleDto;
import com.mmozhzhe.featuretoggle.model.ReleaseDto;

import java.util.Set;

public interface ReleaseToggleServiceInterface {

    ReleaseDto release(ReleaseDto releaseDto) throws ReleaseToggleServiceException;

    Set<ReleaseDto> getAllReleases() throws ReleaseToggleServiceException;

}
