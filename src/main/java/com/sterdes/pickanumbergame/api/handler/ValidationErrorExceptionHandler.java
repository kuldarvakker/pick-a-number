package com.sterdes.pickanumbergame.api.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationErrorExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrors handleErrors(MethodArgumentNotValidException e) {
        var error = new ValidationErrors();
        var fieldErrors = e.getBindingResult().getFieldErrors();
        var reasons = fieldErrors.stream()
                .map(it -> mapToValidationError(it))
                .toList();
        error.setErrors(reasons);
        return error;
    }

    private ValidationError mapToValidationError(FieldError fieldError) {
        var reason = new ValidationError();
        reason.setCode(fieldError.getCodes()[0]);

        var arg = Arrays.stream(fieldError.getArguments())
                .filter(args -> !(args instanceof DefaultMessageSourceResolvable))
                .map(String::valueOf)
                .collect(Collectors.toList());
        reason.setArguments(arg);
        return reason;
    }
}
