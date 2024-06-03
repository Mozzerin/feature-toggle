package com.mmozhzhe.featuretoggle.exception;

public class CustomerServiceException extends Exception {

    public CustomerServiceException(String s, Exception e) {
        super(s, e);
    }

    public CustomerServiceException(String s) {
        super(s);
    }
}
