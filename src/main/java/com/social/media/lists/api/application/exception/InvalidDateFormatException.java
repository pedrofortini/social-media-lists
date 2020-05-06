package com.social.media.lists.api.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidDateFormatException extends RuntimeException{

    private static final long serialVersionUID = -1272508288712731125L;

    public InvalidDateFormatException(String message) {
        super(message);
    }
}
