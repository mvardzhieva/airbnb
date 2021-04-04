package airbnb.controller;

import airbnb.exceptions.BadRequestException;
import airbnb.model.dto.user.*;
import airbnb.model.pojo.User;
import airbnb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@RestController
public class UserController extends AbstractController {
    private UserService userService;
    private SessionManager sessionManager;

    @Autowired
    public UserController(UserService userService, SessionManager sessionManager) {
        this.userService = userService;
        this.sessionManager = sessionManager;
    }

    @PutMapping("/users")
    public RegisterResponseUserDTO register(@RequestBody RegisterRequestUserDTO requestUserDTO, HttpSession session)
            throws IOException, InterruptedException {
        RegisterResponseUserDTO responseUserDTO = new RegisterResponseUserDTO(userService.register(requestUserDTO));
        sessionManager.loginUser(session, responseUserDTO.getId());
        return responseUserDTO;
    }

    @PostMapping("/users")
    public UserProfileDTO login(@RequestBody LoginUserDTO loginUserDTO, HttpSession session) {
        UserProfileDTO userProfileDTO = new UserProfileDTO(userService.login(loginUserDTO));
        sessionManager.loginUser(session, userProfileDTO.getId());
        return userProfileDTO;
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        sessionManager.logoutUser(session);
    }

    @GetMapping("/users/{id}")
    public UserProfileDTO getById(@PathVariable int id) {
        return new UserProfileDTO(userService.getUserById(id));
    }

    @PostMapping("/users/{id}")
    public UserProfileDTO edit(@PathVariable int id,
                               @RequestBody EditUserDTO editUserDTO,
                               HttpSession session) throws IOException, InterruptedException {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != id) {
            throw new BadRequestException("You cannot edit another user's profile.");
        }
        return new UserProfileDTO(userService.edit(user, editUserDTO));
    }

    @DeleteMapping("/users/{id}")
    public UserProfileDTO delete(@PathVariable int id, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != id) {
            throw new BadRequestException("You cannot delete another user's profile.");
        }
        return new UserProfileDTO(userService.delete(user));
    }

    @GetMapping("/users/earnedMostMoney")
    public UserProfileDTO getUserEarnedTheMostMoney() throws SQLException {
        return new UserProfileDTO(userService.getUserEarnedTheMostMoney());
    }

    @GetMapping("/users/mostBookings")
    public UserProfileDTO getUserWithMostBookings() throws SQLException {
        return new UserProfileDTO(userService.getUserWithMostBookings());
    }
}
