package com.sfa22.ScaleTickets.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailSendException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.mail.internet.AddressException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.error("Caught exception: ", exception);

        List<String> errorMessages = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));

        return new ResponseEntity<>(String.join("\n", errorMessages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>(exception.getConstraintViolations().iterator().next().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Invalid date format. Please try again",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<String> handleUnsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Invalid request parameters", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Username not found. Please try again", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressException.class)
    public ResponseEntity<String> handleAddressException(AddressException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Please enter your email address or your phone number", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<String> handleMailSendException(MailSendException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Please enter your email address correctly", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameterExceptionException(MissingServletRequestParameterException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Invalid request parameters", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserInputException.class)
    public ResponseEntity<String> handleInvalidUserInputException(InvalidUserInputException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnavailableTransportException.class)
    public ResponseEntity<String> handleUnavailableTransport(UnavailableTransportException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Method Argument Type is Mismatched", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceMissingException.class)
    public ResponseEntity<String> handleResourceMissing(ResourceMissingException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>(exception.getMissingResourceName() + " not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoMoreSeatsException.class)
    public ResponseEntity<String> handleNoMoreSeats(NoMoreSeatsException exception) {
        logger.info("Caught exception: ", exception);
        return new ResponseEntity<>("No Seats Available", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExists(AlreadyExistsException exception) {
        logger.info("Caught exception: ", exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleInvalidNumberFormat(NumberFormatException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("String is NAN", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Database issues, try again later", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidBody(HttpMessageNotReadableException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Invalid request body", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value
            = {IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<String> handleConflict(RuntimeException exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Something went wrong", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<String> handleAccessDeniedException(Exception exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedException(Exception exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<String> handleArithmeticException(Exception exception) {
        logger.error("Caught exception: ", exception);
        return new ResponseEntity<>("Negative or zero values not subject to calculation.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}