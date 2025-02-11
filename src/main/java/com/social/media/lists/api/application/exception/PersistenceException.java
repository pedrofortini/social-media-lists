package com.social.media.lists.api.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PersistenceException extends RuntimeException {

    private static final long serialVersionUID = 5613503359762451106L;

    public PersistenceException(String message) {
        super(message);
    }
}
