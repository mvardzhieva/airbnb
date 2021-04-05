package airbnb.controller;

import airbnb.exceptions.BadRequestException;
import airbnb.model.dto.booking.AddRequestBookingDTO;
import airbnb.model.pojo.Booking;
import airbnb.model.pojo.User;
import airbnb.services.interfaces.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
public class BookingController extends AbstractController {
    private BookingService bookingService;
    private SessionManager sessionManager;

    @Autowired
    public BookingController(BookingService bookingService, SessionManager sessionManager) {
        this.bookingService = bookingService;
        this.sessionManager = sessionManager;
    }

    @PutMapping("/users/{id}/bookings")
    public Booking add(@PathVariable int id,
                       @Valid @RequestBody AddRequestBookingDTO addBookingDTO,
                       HttpSession session) throws SQLException {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != id) {
            throw new BadRequestException("You cannot add booking for another user.");
        }
        return bookingService.add(user, addBookingDTO);
    }

    @GetMapping("/users/{user_id}/bookings/{booking_id}")
    public Booking getById(@PathVariable(name = "user_id") int userId,
                           @PathVariable(name = "booking_id") Long bookingId,
                           HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != userId) {
            throw new BadRequestException("You cannot access another user's booking.");
        }
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping("/users/{id}/bookings/upcoming")
    public List<Booking> getUpcomingUserBookings(@PathVariable int id, HttpSession session) {
        User user = getUserIfCanAccessBookings(id, session);
        return bookingService.getUpcomingBookings(user);
    }

    @GetMapping("/users/{id}/bookings/current")
    public List<Booking> getCurrentUserBookings(@PathVariable int id, HttpSession session) {
        User user = getUserIfCanAccessBookings(id, session);
        return bookingService.getCurrentBookings(user);
    }

    @GetMapping("/users/{id}/bookings/finished")
    public List<Booking> getFinishedUserBookings(@PathVariable int id, HttpSession session) {
        User user = getUserIfCanAccessBookings(id, session);
        return bookingService.getFinishedBookings(user);
    }

    @DeleteMapping("/users/{user_id}/bookings/{booking_id}")
    public Booking cancel(@PathVariable(name = "user_id") int userId,
                          @PathVariable(name = "booking_id") Long bookingId,
                          HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != userId) {
            throw new BadRequestException("You cannot cancel another user's booking.");
        }
        return bookingService.cancel(userId, bookingId);
    }

    public User getUserIfCanAccessBookings(int userId, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != userId) {
            throw new BadRequestException("You cannot access another user's bookings.");
        }
        return user;
    }
}
