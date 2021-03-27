package airbnb.controller;

import airbnb.model.dto.RegisterRequestUserDTO;
import airbnb.model.dto.RegisterResponseUserDTO;
import airbnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;

    @PutMapping("/Airbnb/users/register")
    public RegisterResponseUserDTO register(@RequestBody RegisterRequestUserDTO requestUserDTO) {
        return userService.register(requestUserDTO);
    }


}
