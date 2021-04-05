package airbnb.services;

import airbnb.exceptions.user.EmailAlreadyRegisteredException;
import airbnb.exceptions.user.NotMatchingPasswordsException;
import airbnb.exceptions.user.UserNotFoundException;
import airbnb.model.dao.UserDAO;
import airbnb.model.dto.user.*;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.User;
import airbnb.model.repositories.UserRepository;
import airbnb.services.interfaces.MediaService;
import airbnb.services.interfaces.PropertyService;
import airbnb.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PropertyService propertyService;
    private UserDAO userDAO;
    private PasswordEncoder encoder;
    private Validator validator;
    private MediaService mediaService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PropertyService propertyService,
                       UserDAO userDAO,
                       MediaService mediaService) {
        this.userRepository = userRepository;
        this.propertyService = propertyService;
        this.userDAO = userDAO;
        this.mediaService = mediaService;
        this.encoder = new BCryptPasswordEncoder();
        this.validator = new Validator();
    }

    public User register(RegisterRequestUserDTO requestUserDTO) throws IOException, InterruptedException {
        validateRegisterUserData(requestUserDTO);
        requestUserDTO.setPassword(encoder.encode(requestUserDTO.getPassword()));
        User user = new User(requestUserDTO);
        user = userRepository.save(user);
        return user;
    }

    public User login(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail());
        if (user == null || !encoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid email/password combination.");
        }
        return user;
    }

    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserNotFoundException("User with this id is not registered.");
    }

    public User edit(User user, EditUserDTO editUserDTO) throws IOException, InterruptedException {
        validator.validateUserInput(editUserDTO.getFirstName(), editUserDTO.getLastName(),
                editUserDTO.getPhoneNumber());
        if (!user.getEmail().equals(editUserDTO.getEmail())) {
            checkIfEmailExists(editUserDTO.getEmail());
            user.setEmail(editUserDTO.getEmail());
        }
        if (!encoder.matches(editUserDTO.getPassword(), user.getPassword())) {
            validator.validatePassword(editUserDTO.getPassword());
            user.setPassword(encoder.encode(editUserDTO.getPassword()));
        }
        user.setFirstName(editUserDTO.getFirstName());
        user.setLastName(editUserDTO.getLastName());
        user.setPhoneNumber(editUserDTO.getPhoneNumber());
        user.setDateOfBirth(editUserDTO.getDateOfBirth());
        user.setAddress(editUserDTO.getAddress());
        user.setGender(editUserDTO.getGender());
        user.setGovernmentId(editUserDTO.getGovernmentId());
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User delete(User user) {
        for (Property property : user.getProperties()) {
            propertyService.deleteById(property.getId());
            mediaService.deleteAllByPropertyId(property.getId());
        }
        userRepository.deleteById(user.getId());
        return user;
    }

    private void validateRegisterUserData(RegisterRequestUserDTO requestUserDTO) throws IOException, InterruptedException {
        validator.validateUserInput(requestUserDTO.getFirstName(), requestUserDTO.getLastName(),
                requestUserDTO.getPhoneNumber());
        checkIfEmailExists(requestUserDTO.getEmail());
        validator.validatePassword(requestUserDTO.getPassword());
        if (!requestUserDTO.getPassword().equals(requestUserDTO.getConfirmedPassword())) {
            throw new NotMatchingPasswordsException("Password and confirmed password do not match.");
        }
    }

    private void checkIfEmailExists(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new EmailAlreadyRegisteredException("This email is already registered.");
        }
    }

    public User getUserEarnedTheMostMoney() throws SQLException {
        return userDAO.getUserEarnedMostMoneyFromBookingForLastYear();
    }

    public User getUserWithMostBookings() throws SQLException {
        return userDAO.getUserEarnedMostMoneyFromBookingForLastYear();
    }
}
