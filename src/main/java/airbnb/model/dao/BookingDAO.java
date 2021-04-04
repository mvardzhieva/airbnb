package airbnb.model.dao;

import airbnb.AirbnbApplication;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

@Component
public class BookingDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isPropertyAlreadyBooked(Long propertyId,
                                           LocalDate startDate,
                                           LocalDate endDate) throws SQLException {
        String query = "SELECT bookings.id\n" +
                "FROM properties\n" +
                "JOIN bookings\n" +
                "ON properties.id = bookings.property_id\n" +
                "WHERE properties.id = ?\n" +
                "AND ? BETWEEN bookings.start_date AND bookings.end_date\n" +
                "OR ? BETWEEN bookings.start_date AND bookings.end_date;";
        try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, propertyId);
            statement.setDate(2, Date.valueOf(startDate));
            statement.setDate(3, Date.valueOf(endDate));
            ResultSet rows = statement.executeQuery();
            return rows.next();
        }
    }
}
