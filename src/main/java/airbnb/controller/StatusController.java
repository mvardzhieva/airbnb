package airbnb.controller;

import airbnb.model.pojo.Booking;
import airbnb.model.pojo.BookingStatusType;
import airbnb.model.repositories.BookingRepository;
import airbnb.model.repositories.BookingStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class StatusController {
    private static final String EVERY_DAY_AT_MIDNIGHT = "0 0 0 * * *";

    private BookingRepository bookingRepository;
    private BookingStatusRepository bookingStatusRepository;

    @Autowired
    public StatusController(BookingRepository bookingRepository,
                            BookingStatusRepository bookingStatusRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingStatusRepository = bookingStatusRepository;
    }

    @Scheduled(cron = EVERY_DAY_AT_MIDNIGHT)
    public void updateBookingsStatus() {
        List<Booking> bookings = bookingRepository
                .getAllByBookingStatusIsNot(bookingStatusRepository.findByName(BookingStatusType.FINISHED));
        for (Booking booking : bookings) {
            if (booking.getStartDate().isEqual(LocalDate.now())) {
                booking.setBookingStatus(bookingStatusRepository.findByName(BookingStatusType.CURRENT));
            } else if (booking.getEndDate().isEqual(LocalDate.now())) {
                booking.setBookingStatus(bookingStatusRepository.findByName(BookingStatusType.FINISHED));
            }
            bookingRepository.save(booking);
        }
    }
}
