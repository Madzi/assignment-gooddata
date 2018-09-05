package com.gooddata.web;

import com.gooddata.domain.model.BadWordCategoryException;
import com.gooddata.web.dto.ApiError;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class WordsControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ BadWordCategoryException.class, IllegalStateException.class, NoSuchElementException.class })
    @ResponseBody
    public ResponseEntity<ApiError> handleBadCategory(final Exception ex, final WebRequest request) {
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
