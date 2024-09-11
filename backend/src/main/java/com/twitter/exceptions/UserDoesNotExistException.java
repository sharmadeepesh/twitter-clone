package com.twitter.exceptions;

import java.io.Serial;

public class UserDoesNotExistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserDoesNotExistException() {
        super("User does not exist yet");
    }
}
