package com.mmozhzhe.featuretoggle.exception;

public class ReleaseToggleServiceException extends Exception {

    public ReleaseToggleServiceException(String s, Exception e) {
        super(s, e);
    }

    public ReleaseToggleServiceException(String s) {
        super(s);
    }
}
