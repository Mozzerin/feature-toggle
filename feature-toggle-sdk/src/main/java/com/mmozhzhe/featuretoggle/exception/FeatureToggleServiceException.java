package com.mmozhzhe.featuretoggle.exception;

public class FeatureToggleServiceException extends Exception {

    public FeatureToggleServiceException(String s, Exception e) {
        super(s, e);
    }

    public FeatureToggleServiceException(String s) {
        super(s);
    }
}
