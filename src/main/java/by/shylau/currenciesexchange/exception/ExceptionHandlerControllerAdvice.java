package by.shylau.currenciesexchange.exception;

import by.shylau.currenciesexchange.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorMessage> handleBadRequestException(RuntimeException ex) {

        return new ResponseEntity<>(ErrorMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleNotFoundException(RuntimeException ex) {

        return new ResponseEntity<>(ErrorMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<ErrorMessage> handleConflictException(RuntimeException ex) {

        return new ResponseEntity<>(ErrorMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build(),
                HttpStatus.CONFLICT);
    }
}