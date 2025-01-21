package com.fiap.challenge.video.app.adapter.input.web.advice;

import com.fiap.challenge.video.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.video.core.common.exception.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataException.class)
    public List<String> invalidDataException(InvalidDataException exception) {
        return exception.getMessages();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void entityNotFoundException(EntityNotFoundException exception) {
        // Return 404 with empty response.
    }

}
