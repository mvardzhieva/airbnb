package airbnb.controller;

import airbnb.exceptions.BadRequestException;
import airbnb.model.dto.user.*;
import airbnb.model.pojo.User;
import airbnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    public RegisterResponseUserDTO register(@RequestBody RegisterRequestUserDTO requestUserDTO, HttpSession session) {
        RegisterResponseUserDTO responseUserDTO = userService.register(requestUserDTO);
        sessionManager.loginUser(session, responseUserDTO.getId());
        return responseUserDTO;
    }

    @PostMapping("/users")
    public UserProfileDTO login(@RequestBody LoginUserDTO loginUserDTO, HttpSession session) {
        UserProfileDTO userProfileDTO = userService.login(loginUserDTO);
        sessionManager.loginUser(session, userProfileDTO.getId());
        return userProfileDTO;
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        sessionManager.logoutUser(session);
    }

    @GetMapping("/users/{id}")
    public UserProfileDTO getById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users/{id}")
    public UserProfileDTO edit(@PathVariable int id, @RequestBody EditUserDTO editUserDTO, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if(user.getId() != id){
            throw new BadRequestException("You cannot edit another user's profile.");
        }
        return userService.edit(user, editUserDTO);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }
}
