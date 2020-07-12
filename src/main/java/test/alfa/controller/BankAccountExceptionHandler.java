package test.alfa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import test.alfa.exceptions.BankAccountAlreadyClosedException;
import test.alfa.exceptions.BankAccountNotFoundException;
import test.alfa.model.view.ErrorResponse;
import test.alfa.model.view.Response;

/**
 * @author a.trofimov 12.07.2020
 */
@RestControllerAdvice
@Slf4j
public class BankAccountExceptionHandler {

    private final static int NOT_FOUND = 1;

    private final static int ALREADY_CLOSED = 2;

    /**
     * Если бы я запретил красное сальдо
     */
    private final static int INSUFFICIENT_FUNDS = 3;

    private final static int UNDEFINED_ERROR = 999;


    @ExceptionHandler(BankAccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handle(BankAccountNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new Response(ErrorResponse.builder().code(NOT_FOUND).message("bank account not found").build());
    }


    @ExceptionHandler(BankAccountAlreadyClosedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response handle(BankAccountAlreadyClosedException exception) {
        log.error(exception.getMessage(), exception);
        return new Response(ErrorResponse.builder().code(ALREADY_CLOSED).message("bank account already closed").build());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handle(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new Response(ErrorResponse.builder().code(UNDEFINED_ERROR).message("Undefined error").build());
    }


}
