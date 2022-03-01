package ru.t1.bank.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.bank.exceptions.IncorrectDataException;
import ru.t1.bank.exceptions.InsufficientFundsException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.Response;

import javax.validation.ConstraintViolationException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleNotFoundException(NotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<Response> handleIncorrectDataException(IncorrectDataException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Response> handleInsufficientFundsException(InsufficientFundsException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleConstraintViolationException(ConstraintViolationException e) {
        Response response = new Response("Insufficient funds on the account");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
