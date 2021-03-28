package airbnb.controller;

import airbnb.exceptions.EmailAlreadyRegisteredException;
import airbnb.exceptions.NotMatchingPasswordsException;
import airbnb.exceptions.UserNotFoundException;
import airbnb.exceptions.UserNotLoggedException;
import airbnb.model.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class Controller {
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleEmailAlreadyRegistered(EmailAlreadyRegisteredException e) {
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(NotMatchingPasswordsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleNotMatchingPasswords(NotMatchingPasswordsException e) {
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDTO handleUserNotFound(UserNotFoundException e) {
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(UserNotLoggedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDTO handleUserNotFound(UserNotLoggedException e) {
        return new ExceptionDTO(e.getMessage());
    }
}
