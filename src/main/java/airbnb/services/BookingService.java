package airbnb.services;

import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.property.PropertyNotAvailableException;
import airbnb.exceptions.NotFoundException;
import airbnb.exceptions.user.InvalidUserInputException;
import airbnb.model.dto.booking.AddRequestBookingDTO;
import airbnb.model.pojo.Booking;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.User;
import airbnb.model.repositories.BookingRepository;
import airbnb.model.repositories.PropertyRepository;
import airbnb.model.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private StatusRepository statusRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          PropertyRepository propertyRepository,
                          StatusRepository statusRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.statusRepository = statusRepository;
    }

    public Booking add(User user, AddRequestBookingDTO addBookingDTO) {
        if (addBookingDTO.getStartDate().isBefore(LocalDate.now())
                || addBookingDTO.getEndDate().isBefore(addBookingDTO.getStartDate())) {
            throw new InvalidUserInputException("You have entered invalid dates.");
        }
        Optional<Property> property = propertyRepository.findById(addBookingDTO.getPropertyId());
        if (property.isEmpty()) {
            throw new NotFoundException("Property with this id does not exists.");
        }
        if (!property.get().getIsFree()) {
            throw new PropertyNotAvailableException("This property is not available on these dates.");
        }
        Booking booking = new Booking(addBookingDTO);
        booking.setUser(user);
        booking.setProperty(property.get());
        booking.setStatusId(statusRepository.findByName("upcoming").getId());
        bookingRepository.save(booking);
        return booking;
    }

    public Booking getBookingById(int userId, Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            throw new NotFoundException("Booking with this id does not exists.");
        }
        int guestId = booking.get().getUser().getId();
        int hostId = booking.get().getProperty().getHost().getId();
        if (userId != guestId && userId != hostId) {
            throw new BadRequestException("You are neither a guest nor a host of this booking.");
        }
        return booking.get();
    }

    public List<Booking> getUpcomingBookings(User user) {
        int upcomingStatusId = statusRepository.findByName("upcoming").getId();
        List<Booking> bookings = bookingRepository.getAllByStatusIdAndUser(upcomingStatusId, user);
        return Collections.unmodifiableList(bookings);
    }

    public List<Booking> getFinishedBookings(User user) {
        int finishedStatusId = statusRepository.findByName("finished").getId();
        List<Booking> bookings = bookingRepository.getAllByStatusIdAndUser(finishedStatusId, user);
        return Collections.unmodifiableList(bookings);
    }

    public List<Booking> getCurrentBookings(User user) {
        int currentStatusId = statusRepository.findByName("current").getId();
        List<Booking> bookings = bookingRepository.getAllByStatusIdAndUser(currentStatusId, user);
        return Collections.unmodifiableList(bookings);
    }

    public Booking cancel(int userId, Long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new NotFoundException("Booking with this id does not exists.");
        }
        Booking booking = optionalBooking.get();
        int guestId = booking.getUser().getId();
        if (userId != guestId) {
            throw new BadRequestException("You cannot cancel another user's booking.");
        }
        int minDaysToCancelBeforeBookingStarts = 3;
        if (LocalDate.now().plusDays(minDaysToCancelBeforeBookingStarts).isAfter(booking.getStartDate())) {
            throw new BadRequestException("You cannot cancel your booking when there are less than 3 days before it starts.");
        }
        bookingRepository.deleteById(bookingId);
        return booking;
    }

}
