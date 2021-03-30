package airbnb.controller;

import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.PropertyNotAvailableException;
import airbnb.exceptions.user.*;
import airbnb.exceptions.NotFoundException;
import airbnb.model.dto.ExceptionDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractController {

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

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleNotFound(NotFoundException e) {
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleBadRequest(BadRequestException e) {
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(InvalidUserInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleInvalidUserInput(InvalidUserInputException e) {
        return new ExceptionDTO(e.getMessage());
    }

<<<<<<< HEAD
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO handleDataAccess(DataAccessException e) {
        return new ExceptionDTO("Error!");
    }

=======
    @ExceptionHandler(PropertyNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleBookedProperty(PropertyNotAvailableException e) {
        return new ExceptionDTO(e.getMessage());
    }
>>>>>>> 039bcae90c6399557951e354a894ab4f9582ad4e
}
