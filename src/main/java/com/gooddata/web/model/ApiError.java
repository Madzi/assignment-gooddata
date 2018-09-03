package com.gooddata.web.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.http.HttpStatus;

public class ApiError {

    private HttpStatus status;
    private Long timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    private ApiError() {
        timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    }

    public ApiError(final HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(final HttpStatus status, final Throwable exception) {
        this(status);
        message = "Unexpected error";
        debugMessage = exception.getLocalizedMessage();
    }

    public ApiError(final HttpStatus status, final String message, final Throwable exception) {
        this(status, exception);
        this.message = message;
    }

}
