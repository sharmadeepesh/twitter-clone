package com.twitter.exceptions;

public class EmailFailedToSendException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailFailedToSendException() {
        super("Couldn't send the email. Please debug");
    }
}
