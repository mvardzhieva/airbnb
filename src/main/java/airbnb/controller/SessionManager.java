package airbnb.controller;

import airbnb.exceptions.AuthenticationException;
import airbnb.exceptions.user.UserNotLoggedException;
import airbnb.model.pojo.User;
import airbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
//@Scope(scopeName = "session")
public class SessionManager extends AbstractController {

    private static final String LOGGED_USER_ID = "LOGGED_USER_ID";
    protected final String EXCEPTION_MSG = "Action Unauthorized!";
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

    //TODO REWORK
    public void loginUser(HttpSession session, int userId) {
        session.setAttribute(LOGGED_USER_ID, userId);
//        session.
    }

    public void logoutUser(HttpSession session) {
        session.invalidate();
    }

    public void validate(Long id, HttpSession session) {
        if (this.getLoggedUser(session).getId() != id) {
            throw new AuthenticationException(EXCEPTION_MSG);
        }
    }
}