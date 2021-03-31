package airbnb.services;

import airbnb.exceptions.user.EmailAlreadyRegisteredException;
import airbnb.exceptions.user.NotMatchingPasswordsException;
import airbnb.exceptions.user.UserNotFoundException;
import airbnb.model.dto.user.*;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.User;
import airbnb.model.repositories.UserRepository;
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
//    private PropertyServiceImpl propertyService;
    private PasswordEncoder encoder;
    private Validator validator;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.propertyService = propertyService;
        this.encoder = new BCryptPasswordEncoder();
        this.validator = new Validator();
    }

    public RegisterResponseUserDTO register(RegisterRequestUserDTO requestUserDTO) {
        validateRegisterUserData(requestUserDTO);
        requestUserDTO.setPassword(encoder.encode(requestUserDTO.getPassword()));
        User user = new User(requestUserDTO);
        user = userRepository.save(user);
        return new RegisterResponseUserDTO(user);
    }

    public UserProfileDTO login(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail());
        if (user == null || !encoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid email/password combination.");
        }
        return new UserProfileDTO(user);
    }

    public UserProfileDTO getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new UserProfileDTO(user.get());
        }
        throw new UserNotFoundException("User with this id is not registered.");
    }

    public UserProfileDTO edit(User user, EditUserDTO editUserDTO) {
        validator.validateUserInput(editUserDTO.getFirstName(), editUserDTO.getLastName(),
                user.getEmail(), editUserDTO.getPhoneNumber());
        if (!user.getEmail().equals(editUserDTO.getEmail())) {
            checkIfEmailExists(editUserDTO.getEmail());
            user.setEmail(editUserDTO.getEmail());
        }
        user.setFirstName(editUserDTO.getFirstName());
        user.setLastName(editUserDTO.getLastName());
        user.setPassword(encoder.encode(editUserDTO.getPassword()));
        user.setPhoneNumber(editUserDTO.getPhoneNumber());
        user.setDateOfBirth(editUserDTO.getDateOfBirth());
        user.setAddress(editUserDTO.getAddress());
        user.setGender(editUserDTO.getGender());
        user.setGovernmentId(editUserDTO.getGovernmentId());
        userRepository.save(user);
        return new UserProfileDTO(user);
    }

    @Transactional
    public UserProfileDTO delete(User user) {
        UserProfileDTO deletedUserProfile = new UserProfileDTO(user);
        for (Property property : deletedUserProfile.getProperties()) {
            System.out.println(property.getId());
//            propertyService.deleteById(property.getId());
        }
        userRepository.deleteById(user.getId());
        return deletedUserProfile;
    }

    private void validateRegisterUserData(RegisterRequestUserDTO requestUserDTO) {
        validator.validateUserInput(requestUserDTO.getFirstName(), requestUserDTO.getLastName(),
                requestUserDTO.getEmail(), requestUserDTO.getPhoneNumber());
        checkIfEmailExists(requestUserDTO.getEmail());
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
