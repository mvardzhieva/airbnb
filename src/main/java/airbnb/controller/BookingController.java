package airbnb.controller;

import airbnb.exceptions.BadRequestException;
import airbnb.model.dto.booking.AddRequestBookingDTO;
import airbnb.model.pojo.Booking;
import airbnb.model.pojo.User;
import airbnb.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public Booking add(@PathVariable int id, @RequestBody AddRequestBookingDTO addBookingDTO, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != id) {
            throw new BadRequestException("You cannot add booking for another user.");
        }
        return bookingService.add(user, addBookingDTO);
    }

    @GetMapping("/users/{user_id}/bookings/{booking_id}")
    public Booking getById(@PathVariable(name = "user_id") int userId, @PathVariable(name = "booking_id") Long bookingId, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != userId) {
            throw new BadRequestException("You are neither a guest nor a host of this booking.");
        }
        return bookingService.getBookingById(bookingId);
    }

    @GetMapping("/users/{id}/bookings/upcoming")
    public List<Booking> getUpcomingUserBookings(@PathVariable int id, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != id) {
            throw new BadRequestException("You cannot access another user's bookings.");
        }
        return bookingService.getUpcomingBookings(user);
    }

    @GetMapping("/users/{id}/bookings/current")
    public List<Booking> getCurrentUserBookings(@PathVariable int id, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != id) {
            throw new BadRequestException("You cannot access another user's bookings.");
        }
        return bookingService.getCurrentBookings(user);
    }

    @GetMapping("/users/{id}/bookings/finished")
    public List<Booking> getFinishedUserBookings(@PathVariable int id, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != id) {
            throw new BadRequestException("You cannot access another user's bookings.");
        }
        return bookingService.getFinishedBookings(user);
    }
}
