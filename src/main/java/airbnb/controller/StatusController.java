package airbnb.controller;

import airbnb.model.pojo.Booking;
import airbnb.model.repositories.BookingRepository;
import airbnb.model.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class StatusController {
    private BookingRepository bookingRepository;
    private StatusRepository statusRepository;

    @Autowired
    public StatusController(BookingRepository bookingRepository, StatusRepository statusRepository) {
        this.bookingRepository = bookingRepository;
        this.statusRepository = statusRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateBookingsStatus() {
        List<Booking> bookings = bookingRepository
                .getAllByStatusIdIsNot(statusRepository.findByName("finished").getId());
        for (Booking booking : bookings) {
            if (booking.getStartDate().isEqual(LocalDate.now())
                    || booking.getStartDate().isBefore(LocalDate.now())) {
                booking.setStatusId(statusRepository.findByName("current").getId());
            }
            if (booking.getEndDate().isBefore(LocalDate.now())) {
                booking.setStatusId(statusRepository.findByName("finished").getId());
            }
            bookingRepository.save(booking);
        }
    }
}
