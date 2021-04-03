package airbnb.services;


import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.NotFoundException;
import airbnb.model.pojo.Booking;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.Rating;
import airbnb.model.pojo.User;
import airbnb.model.repositories.RatingRepository;
import airbnb.services.interfaces.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Primary
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private BookingService bookingService;
    private UserService userService;
    private PropertyServiceImpl propertyService;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository,
                             BookingService bookingService,
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
            throw new NotFoundException("Ratings not found!");
        }

        return ratings;
    }

    @Override
    public Rating findAvgByPropertyId(Long propertyId) {
        List<Rating> ratings = ratingRepository.findAllByPropertyId(propertyId);
        Rating rating = new Rating();
        Property property = propertyService.getById(propertyId);

        if (ratings.isEmpty()) {
            new Rating(0L,0.0f,0f,0f,0f,0f,0f,property);
        }

        for (Rating r : ratings) {

        }

        return rating;
    }

    @Override
    public Rating add(Long userId, Long propertyId, Rating rating) {
        User user = userService.getUserById(userId.intValue());
        Property property = propertyService.getById(propertyId);
        List<Booking> bookings = bookingService.getFinishedBookings(user);

        for (Booking booking : bookings) {
            if (booking.getProperty().getId().equals(propertyId)) {
                rating.setProperty(property);
//                booking.set
                return ratingRepository.save(rating);
            }
        }

        //TODO Validate 1 rating per booking or all in one place

        throw new BadRequestException("You need finished booking for that " +
                "property to be able to add rating!");
    }

    @Override
    public Rating edit(Long userId, Long ratingId, Long propertyId, Rating rating) {
        User user = userService.getUserById(userId.intValue());
        List<Booking> bookings = bookingService.getFinishedBookings(user);
        Property property = propertyService.getById(propertyId);

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


    //TODO REFACTOR FOR .isEmpty() and findallratingsbypropertyid
}
