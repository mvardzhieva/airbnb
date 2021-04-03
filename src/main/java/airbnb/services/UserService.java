package airbnb.services;

import airbnb.exceptions.user.EmailAlreadyRegisteredException;
import airbnb.exceptions.user.NotMatchingPasswordsException;
import airbnb.exceptions.user.UserNotFoundException;
import airbnb.model.dto.user.*;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.User;
import airbnb.model.repositories.UserRepository;
import airbnb.services.interfaces.PropertyService;
import airbnb.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PropertyService propertyService;
    private PasswordEncoder encoder;
    private Validator validator;

    @Autowired
    public UserService(UserRepository userRepository, PropertyService propertyService) {
        this.userRepository = userRepository;
        this.propertyService = propertyService;
        this.encoder = new BCryptPasswordEncoder();
        this.validator = new Validator();
    }

    public User register(RegisterRequestUserDTO requestUserDTO) {
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

    public User edit(User user, EditUserDTO editUserDTO) {
        validator.validateUserInput(editUserDTO.getFirstName(), editUserDTO.getLastName(),
                editUserDTO.getEmail(), editUserDTO.getPhoneNumber());
        if (!user.getEmail().equals(editUserDTO.getEmail())) {
            checkIfEmailExists(editUserDTO.getEmail());
            user.setEmail(editUserDTO.getEmail());
        }
        if (!encoder.matches(editUserDTO.getPassword(), user.getPassword())) {
            validator.isPasswordCompromised(editUserDTO.getPassword());
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
        }
        userRepository.deleteById(user.getId());
        return user;
    }

    private void validateRegisterUserData(RegisterRequestUserDTO requestUserDTO) {
        validator.validateUserInput(requestUserDTO.getFirstName(), requestUserDTO.getLastName(),
                requestUserDTO.getEmail(), requestUserDTO.getPhoneNumber());
        checkIfEmailExists(requestUserDTO.getEmail());
        validator.isPasswordCompromised(requestUserDTO.getPassword());
        if (!requestUserDTO.getPassword().equals(requestUserDTO.getConfirmedPassword())) {
            throw new NotMatchingPasswordsException("Password and confirmed password do not match.");
        }
    }

    private void checkIfEmailExists(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new EmailAlreadyRegisteredException("This email is already registered.");
        }
    }

}
