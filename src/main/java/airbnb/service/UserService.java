package airbnb.service;

import airbnb.exceptions.EmailAlreadyRegisteredException;
import airbnb.exceptions.NotMatchingPasswordsException;
import airbnb.model.dto.RegisterRequestUserDTO;
import airbnb.model.dto.RegisterResponseUserDTO;
import airbnb.model.pojo.User;
import airbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public RegisterResponseUserDTO register(RegisterRequestUserDTO requestUserDTO) {
        validateRegisterUserData(requestUserDTO);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        requestUserDTO.setPassword(encoder.encode(requestUserDTO.getPassword()));
        User user = new User(requestUserDTO);
        user = userRepository.save(user);
        return new RegisterResponseUserDTO(user);
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
