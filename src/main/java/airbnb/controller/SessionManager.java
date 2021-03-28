package airbnb.controller;

import airbnb.exceptions.UserNotFoundException;
import airbnb.exceptions.UserNotLoggedException;
import airbnb.model.pojo.User;
import airbnb.model.repositories.UserRepository;
import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class SessionManager {
    private static final String LOGGED_USER_ID = "LOGGED_USER_ID";

    private UserRepository userRepository;

    @Autowired
    public SessionManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getLoggedUser(HttpSession session) {
        if (session.getAttribute(LOGGED_USER_ID) != null) {
            int userId = (int) session.getAttribute(LOGGED_USER_ID);
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                return user.get();
            }
        }
        throw new UserNotLoggedException("You are not logged in.");
    }

    public void loginUser(HttpSession session, int userId) {
        session.setAttribute(LOGGED_USER_ID, userId);
    }

    public void logoutUser(HttpSession session) {
        session.invalidate();
    }
}