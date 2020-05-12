package com.assignment.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ElasticSearchException extends RuntimeException{

    public ElasticSearchException(String message) {
        super(message);
    }
}
