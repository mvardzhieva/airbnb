package airbnb.services.interfaces;

import airbnb.model.dto.user.EditUserDTO;
import airbnb.model.dto.user.LoginUserDTO;
import airbnb.model.dto.user.RegisterRequestUserDTO;
import airbnb.model.pojo.User;

import java.io.IOException;
import java.sql.SQLException;

public interface UserService {
    User register(RegisterRequestUserDTO requestUserDTO) throws IOException, InterruptedException;

    User login(LoginUserDTO loginUserDTO);

    User getUserById(int id);

    User edit(User user, EditUserDTO editUserDTO) throws IOException, InterruptedException;

    User delete(User user);

    User getUserEarnedTheMostMoney() throws SQLException;

    User getUserWithMostBookings() throws SQLException;
}
