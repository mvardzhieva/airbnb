package airbnb.controller;

import airbnb.model.dto.booking.AddRequestBookingDTO;
import airbnb.model.pojo.Booking;
import airbnb.model.pojo.User;
import airbnb.service.BookingService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class BookingController extends AbstractController {
    private BookingService bookingService;
    private SessionManager sessionManager;

    public BookingController(BookingService bookingService, SessionManager sessionManager) {
        this.bookingService = bookingService;
        this.sessionManager = sessionManager;
    }

    @PutMapping("/bookings")
    public Booking add(@RequestBody AddRequestBookingDTO addBookingDTO, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        return bookingService.add(user, addBookingDTO);
    }

    @GetMapping("/bookings/{id}")
    public Booking getById(@PathVariable Long id, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        return bookingService.getBookingById(user, id);
    }

    @GetMapping("/bookings/upcoming")
    public List<Booking> getUpcomingUserBookings(HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        return bookingService.getUpcomingBookings(user);
    }

    @GetMapping("/bookings/past")
    public List<Booking> getPastUserBookings(HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        return bookingService.getPastBookings(user);
    }
}
