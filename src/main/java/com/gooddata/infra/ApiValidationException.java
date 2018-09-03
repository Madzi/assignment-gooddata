package com.gooddata.infra;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiValidationException extends RuntimeException {

    public ApiValidationException() {
        super();
    }

    public ApiValidationException(final String message) {
        super(message);
    }

    public ApiValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ApiValidationException(final Throwable cause) {
        super(cause);
    }

}
