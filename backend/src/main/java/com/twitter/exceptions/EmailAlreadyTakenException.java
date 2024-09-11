package com.twitter.exceptions;

import java.io.Serial;

public class EmailAlreadyTakenException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailAlreadyTakenException() {
        super("The email is already in use.");
    }

}
