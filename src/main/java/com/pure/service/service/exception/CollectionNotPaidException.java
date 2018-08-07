package com.pure.service.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CollectionNotPaidException extends Exception {

    public CollectionNotPaidException(String message) {
        super(message);
    }
}
