package com.pure.service.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContractsExceedLimitException extends Exception {

    public ContractsExceedLimitException(String message) {
        super(message);
    }
}
