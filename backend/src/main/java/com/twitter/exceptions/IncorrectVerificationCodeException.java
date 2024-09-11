package com.twitter.exceptions;


public class IncorrectVerificationCodeException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public IncorrectVerificationCodeException() {
        super("The verification code you entered is incorrect.");
    }
}
