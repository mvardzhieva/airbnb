package airbnb.services;

import airbnb.exceptions.user.EmailAlreadyRegisteredException;
import airbnb.exceptions.user.NotMatchingPasswordsException;
import airbnb.exceptions.user.UserNotFoundException;
import airbnb.model.dao.UserDAO;
import airbnb.model.dto.user.*;
import airbnb.model.pojo.Booking;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.User;
import airbnb.model.repositories.BookingRepository;
import airbnb.model.repositories.UserRepository;
import airbnb.services.interfaces.PropertyService;
import airbnb.services.interfaces.UserService;
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
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PropertyService propertyService;
    private BookingRepository bookingRepository;
    private UserDAO userDAO;
    private PasswordEncoder encoder;
    private Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PropertyService propertyService,
                           UserDAO userDAO,
                           BookingRepository bookingRepository) {

        this.userRepository = userRepository;
        this.propertyService = propertyService;
        this.bookingRepository = bookingRepository;
        this.userDAO = userDAO;
        this.encoder = new BCryptPasswordEncoder();
        this.validator = new Validator();
    }

    @Override
    public User register(RegisterRequestUserDTO requestUserDTO) throws IOException, InterruptedException {
        validateRegisterUserData(requestUserDTO);
        requestUserDTO.setPassword(encoder.encode(requestUserDTO.getPassword()));
        User user = new User(requestUserDTO);
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User login(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail());
        if (user == null || !encoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid email/password combination.");
        }
        return user;
    }

    @Override
    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserNotFoundException("User with this id is not registered.");
    }

    @Override
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

    @Override
    @Transactional
    public User delete(User user) {
        for (Property property : user.getProperties()) {
            propertyService.deleteById((long) user.getId(), property.getId());
        }
        for (Booking booking : user.getBookings()) {
            bookingRepository.deleteById(booking.getId());
        }
        userRepository.deleteById(user.getId());
        return user;
    }

    @Override
    public User getUserEarnedTheMostMoney() throws SQLException {
        return userDAO.getUserEarnedMostMoneyFromBookingForLastYear();
    }

    @Override
    public User getUserWithMostBookings() throws SQLException {
        return userDAO.getUserWithMostFinishedBookingForLastYear();
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

}
