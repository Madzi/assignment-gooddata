package com.gooddata.domain.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadWordCategoryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadWordCategoryException() {
        super();
    }

    public BadWordCategoryException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public BadWordCategoryException(final String message) {
        super(message);
    }

    public BadWordCategoryException(final Throwable throwable) {
        super(throwable);
    }

}
