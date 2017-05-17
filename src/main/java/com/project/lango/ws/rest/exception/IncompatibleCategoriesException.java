package com.project.lango.ws.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by gatomulesei on 5/16/2017.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Categories for quiz and question do not match!")
public class IncompatibleCategoriesException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IncompatibleCategoriesException(String message){
        super(message);
    }
}
