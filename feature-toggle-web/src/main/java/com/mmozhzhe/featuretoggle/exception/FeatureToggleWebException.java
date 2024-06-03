package com.mmozhzhe.featuretoggle.exception;

public class FeatureToggleWebException extends RuntimeException {

    private final String errorCode;

    public FeatureToggleWebException(String message, String errorCode, Exception e) {
        super(message, e);
        this.errorCode = errorCode;
    }

    public FeatureToggleWebException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
