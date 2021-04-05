package airbnb.services.interfaces;

import airbnb.model.dto.booking.AddRequestBookingDTO;
import airbnb.model.pojo.*;

import java.sql.SQLException;
import java.util.List;

public interface BookingService {
    Booking add(User user, AddRequestBookingDTO addBookingDTO) throws SQLException;

    Booking getBookingById(int userId, Long bookingId);

    List<Booking> getUpcomingBookings(User user);

    List<Booking> getFinishedBookings(User user);

    List<Booking> getCurrentBookings(User user);

    Booking cancel(int userId, Long bookingId);
}
