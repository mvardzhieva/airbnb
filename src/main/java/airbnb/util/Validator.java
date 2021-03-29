package airbnb.util;

import airbnb.exceptions.user.InvalidUserInputException;

public class Validator {
    public void validateUserInput(String firstName, String lastName, String email, String phone) {
        validateName(firstName);
        validateName(lastName);
        validateEmail(email);
        validatePhoneNumber(phone);
    }

    private void validateName(String name) {
        String nameValidationRegex = "([A-Z][a-z]+([ -][A-Z][a-z]+)*){1,99}$";
        if (!name.matches(nameValidationRegex)) {
            throw new InvalidUserInputException("Invalid name syntax.");
        }
    }

    private void validateEmail(String email) {
        String emailValidationRegex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if (!email.matches(emailValidationRegex)) {
            throw new InvalidUserInputException("Invalid email.");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        String phoneValidationRegex = "^\\d{10}$";
        if (!phoneNumber.matches(phoneValidationRegex)) {
            throw new InvalidUserInputException("Invalid phone number.");
        }
    }
}
