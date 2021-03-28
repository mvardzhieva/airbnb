package airbnb.service;

import airbnb.exceptions.EmailAlreadyRegisteredException;
import airbnb.exceptions.NotMatchingPasswordsException;
import airbnb.exceptions.UserNotFoundException;
import airbnb.model.dto.user.*;
import airbnb.model.pojo.User;
import airbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
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
        //TODO validations
        user.setFirstName(editUserDTO.getFirstName());
        user.setLastName(editUserDTO.getLastName());
        user.setPhoneNumber(editUserDTO.getPhoneNumber());
        user.setDateOfBirth(editUserDTO.getDateOfBirth());
        user.setAddress(editUserDTO.getAddress());
        //TODO save gender to db as text

        user.setGender(editUserDTO.getGender());
        user.setGovernmentId(editUserDTO.getGovernmentId());
        userRepository.save(user);
        return new UserProfileDTO(user);
    }

    private void validateRegisterUserData(RegisterRequestUserDTO requestUserDTO) {
        //TODO check if email, phone, names and date of birth are valid
        if (userRepository.findByEmail(requestUserDTO.getEmail()) != null) {
            throw new EmailAlreadyRegisteredException("This email is already registered.");
        }
        if (!requestUserDTO.getPassword().equals(requestUserDTO.getConfirmedPassword())) {
            throw new NotMatchingPasswordsException("Password and confirmed password do not match.");
        }
    }

}
