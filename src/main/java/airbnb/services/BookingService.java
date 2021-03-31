package airbnb.services;

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

    public Booking getBookingById(Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()) {
            return booking.get();
        }
        throw new NotFoundException("Booking with this id does not exists.");
    }

    public List<Booking> getUpcomingBookings(User user) {
        int upcomingStatusId = statusRepository.findByName("upcoming").getId();
        List<Booking> bookings = bookingRepository.getAllByStatusIdAndUser(upcomingStatusId, user);
        if (bookings.isEmpty()) {
            throw new NotFoundException("You have no upcoming bookings.");
        }
        return Collections.unmodifiableList(bookings);
    }

    public List<Booking> getFinishedBookings(User user) {
        int finishedStatusId = statusRepository.findByName("finished").getId();
        List<Booking> bookings = bookingRepository.getAllByStatusIdAndUser(finishedStatusId, user);
        if (bookings.isEmpty()) {
            throw new NotFoundException("You have no past bookings.");
        }
        return Collections.unmodifiableList(bookings);
    }

    public List<Booking> getCurrentBookings(User user) {
        int currentStatusId = statusRepository.findByName("current").getId();
        List<Booking> bookings = bookingRepository.getAllByStatusIdAndUser(currentStatusId, user);
        if (bookings.isEmpty()) {
            throw new NotFoundException("You have no past bookings.");
        }
        return Collections.unmodifiableList(bookings);
    }
}
