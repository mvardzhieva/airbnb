package airbnb.controller;

import airbnb.AirbnbApplication;
import airbnb.exceptions.AuthenticationException;
import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.property.PropertyNotAvailableException;
import airbnb.exceptions.user.*;
import airbnb.exceptions.NotFoundException;
import airbnb.model.dto.exception.ExceptionDTO;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractController {

    private static final Logger LOGGER = LogManager.getLogger(AirbnbApplication.class);

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleEmailAlreadyRegistered(EmailAlreadyRegisteredException e) {
        log(e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(NotMatchingPasswordsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleNotMatchingPasswords(NotMatchingPasswordsException e) {
        log(e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDTO handleUserNotFound(UserNotFoundException e) {
        log(e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(UserNotLoggedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDTO handleUserNotLoggedIn(UserNotLoggedException e) {
        log(e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handleNotFound(NotFoundException e) {
        log(e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleBadRequest(BadRequestException e) {
        log(e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(InvalidUserInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleInvalidUserInput(InvalidUserInputException e) {
        log(e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO handleDataAccess(DataAccessException e) {
        return new ExceptionDTO("Data access error!");
    }

    @ExceptionHandler(PropertyNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleBookedProperty(PropertyNotAvailableException e) {
        log(e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDTO handleAuthentication(AuthenticationException e) {
        log(e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(CompromisedPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleCompromisedPassword(CompromisedPasswordException e) {
        log(e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleArgumentNotValid(MethodArgumentNotValidException e) {
        log(e);
        return new ExceptionDTO("Invalid input!");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleHttpMsgNotReadable(HttpMessageNotReadableException e) {
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(GeoIp2Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handleUserLocationNotFound(GeoIp2Exception e) {
        log(e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO handleInternalServerErrors(Exception e) {
        log(e);
        return new ExceptionDTO("Call or fire the dev! :)");
    }

    private void log(Exception e) {
        LOGGER.error(e.getMessage());
        LOGGER.trace(e.getStackTrace());
    }

}
