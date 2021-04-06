package airbnb.services;


import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.NotFoundException;
import airbnb.model.pojo.Booking;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.Rating;
import airbnb.model.pojo.User;
import airbnb.model.repositories.RatingRepository;
import airbnb.services.interfaces.RatingService;
import airbnb.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private BookingServiceImpl bookingService;
    private UserService userService;
    private PropertyServiceImpl propertyService;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository,
                             BookingServiceImpl bookingService,
                             UserService userService,
                             PropertyServiceImpl propertyService) {
        this.ratingRepository = ratingRepository;
        this.bookingService = bookingService;
        this.userService = userService;
        this.propertyService = propertyService;
    }

    @Override
    public List<Rating> findAllByPropertyId(Long propertyId) {
        List<Rating> ratings = ratingRepository.findAllByPropertyId(propertyId);
        if (ratings.isEmpty()) {
            throw new NotFoundException("Rating not found!");
        }

        return ratings;
    }

    @Override
    public Rating findAvgByPropertyId(Long propertyId) {
        List<Rating> ratings = ratingRepository.findAllByPropertyId(propertyId);
        Property property = propertyService.getByPropertyId(propertyId);
        Rating rating = new Rating(propertyId, 0f, 0f, 0f, 0f, 0f, 0f, property);

        if (ratings.isEmpty()) {
            return rating;
        }

        for (Rating r : ratings) {
            rating.setAccuracy(rating.getAccuracy() + r.getAccuracy());
            rating.setCleanliness(rating.getCleanliness() + r.getCleanliness());
            rating.setCommunication(rating.getCommunication() + r.getCommunication());
            rating.setCheckIn(rating.getCheckIn() + r.getCheckIn());
            rating.setLocation(rating.getLocation() + r.getLocation());
            rating.setValue(rating.getValue() + r.getValue());
        }

        rating.setAccuracy(rating.getAccuracy() / ratings.size());
        rating.setCleanliness(rating.getCleanliness() / ratings.size());
        rating.setCommunication(rating.getCommunication() / ratings.size());
        rating.setCheckIn(rating.getCheckIn() / ratings.size());
        rating.setLocation(rating.getLocation() / ratings.size());
        rating.setValue(rating.getValue() / ratings.size());

        return rating;
    }

    //SQL query to lower time complexity
    @Override
    public Rating add(Long userId, Long propertyId, Rating rating) {
        User user = userService.getUserById(userId.intValue());
        Property property = propertyService.getByPropertyId(propertyId);
        List<Booking> bookings = bookingService.getFinishedBookings(user);

        for (Booking booking : bookings) {
            if (booking.getProperty().getId().equals(propertyId)) {
                rating.setProperty(property);
                return ratingRepository.save(rating);
            }
        }

        throw new BadRequestException("You need finished booking for that " +
                "property to be able to add rating!");
    }

    @Override
    public Rating edit(Long userId, Long ratingId, Long propertyId, Rating rating) {
        User user = userService.getUserById(userId.intValue());
        List<Booking> bookings = bookingService.getFinishedBookings(user);
        Property property = propertyService.getByPropertyId(propertyId);

        for (Booking booking : bookings) {
            if (booking.getProperty().getId().equals(propertyId)) {
                rating.setProperty(property);
                rating.setId(ratingId);
                return ratingRepository.save(rating);
            }
        }

        throw new NotFoundException("Rating not found!");
    }

    @Override
    public void delete(Long userId, Long ratingId, Long propertyId) {
        User user = userService.getUserById(userId.intValue());
        List<Booking> bookings = bookingService.getFinishedBookings(user);

        boolean isBookingFound = false;
        for (Booking booking : bookings) {
            if (booking.getProperty().getId().equals(propertyId)) {
                ratingRepository.deleteById(userId);
                isBookingFound = true;
            }
        }

        if (!isBookingFound) {
            throw new NotFoundException("Rating not found!");
        }
    }
}
