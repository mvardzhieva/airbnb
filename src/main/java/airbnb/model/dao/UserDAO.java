package airbnb.model.dao;

import airbnb.AirbnbApplication;
import airbnb.exceptions.NotFoundException;
import airbnb.model.pojo.User;
import airbnb.model.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Objects;

@Component
public class UserDAO {
    private JdbcTemplate jdbcTemplate;
    private UserRepository userRepository;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public User getUserEarnedMostMoneyFromBookingForLastYear() {
        String query = "SELECT users.id\n" +
                "FROM users\n" +
                "JOIN properties\n" +
                "ON users.id = properties.host_id\n" +
                "JOIN bookings\n" +
                "ON properties.id = bookings.property_id\n" +
                "WHERE bookings.end_date >= DATE_SUB(NOW(), INTERVAL 1 YEAR)\n" +
                "AND bookings.status_id = 3\n" +
                "GROUP BY users.id\n" +
                "ORDER BY SUM(DATEDIFF(bookings.end_date, bookings.start_date) * properties.price) DESC\n" +
                "LIMIT 1;";

        try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rows = statement.executeQuery();
            if (rows.next()) {
                return userRepository.findById(rows.getInt(1)).get();
            }
            throw new NotFoundException("There are no users or bookings made in the past year.");
        } catch (SQLException e) {
            //TODO

            LogManager.getLogger(AirbnbApplication.class).trace(e.getStackTrace());
        }
        return null;
    }

    public User getUserWithMostFinishedBookingForLastNYears(int years) {
        String query = "SELECT users.id\n" +
                "FROM users\n" +
                "JOIN bookings\n" +
                "ON users.id = bookings.user_id\n" +
                "WHERE bookings.end_date >= DATE_SUB(NOW(), INTERVAL ? YEAR)\n" +
                "AND bookings.status_id = 3\n" +
                "GROUP BY users.id\n" +
                "ORDER BY COUNT(users.id) DESC\n" +
                "LIMIT 1;";
        try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, years);
            ResultSet rows = statement.executeQuery();
            if (rows.next()) {
                return userRepository.findById(rows.getInt(1)).get();
            }
            throw new NotFoundException("There are no users or bookings made in the past " + years + "year.");
        } catch (SQLException e) {
            //TODO

            LogManager.getLogger(AirbnbApplication.class).trace(e.getStackTrace());
        }
        return null;
    }

}
