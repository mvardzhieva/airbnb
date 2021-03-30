package airbnb.service;

import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.PropertyNotAvailableException;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private StatusRepository statusRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, PropertyRepository propertyRepository, StatusRepository statusRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.statusRepository = statusRepository;
    }

    public Booking add(User user, AddRequestBookingDTO addBookingDTO) {
        if (addBookingDTO.getEndDate().isBefore(addBookingDTO.getStartDate())) {
            throw new InvalidUserInputException("End date must be after start date.");
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

    public Booking getBookingById(User loggedUser, Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            throw new NotFoundException("Booking with this id does not exists.");
        }
        User guest = booking.get().getUser();
        User host = booking.get().getProperty().getHost();
        if (loggedUser.getId() == guest.getId() || loggedUser.getId() == host.getId()) {
            return booking.get();
        }
        throw new BadRequestException("You are neither a guest nor a host of this booking.");
    }

    public List<Booking> getUpcomingBookings(User user) {
        List<Booking> bookings = user.getBookings()
                .stream()
                .filter(b -> b.getStatusId() == statusRepository.findByName("upcoming").getId()
                        || b.getStatusId() == statusRepository.findByName("current").getId())
                .collect(Collectors.toUnmodifiableList());
        if (bookings.isEmpty()) {
            throw new NotFoundException("You have no upcoming bookings.");
        }
        return bookings;
    }

    public List<Booking> getPastBookings(User user) {
        List<Booking> bookings = user.getBookings()
                .stream()
                .filter(b -> b.getStatusId() == statusRepository.findByName("finished").getId())
                .collect(Collectors.toUnmodifiableList());
        if (bookings.isEmpty()) {
            throw new NotFoundException("You have no past bookings.");
        }
        return bookings;
    }
}
