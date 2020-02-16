package com.fozf.jsoccwebservices.exceptions;

import javax.naming.AuthenticationException;

public class UserNotEnabledException extends AuthenticationException {
    public UserNotEnabledException(String explanation) {
        super(explanation);
    }
}
