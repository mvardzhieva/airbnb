package airbnb.services;

import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.NotFoundException;
import airbnb.model.dto.review.AddRequestReviewDTO;
import airbnb.model.dto.review.EditReviewDTO;
import airbnb.model.pojo.Booking;
import airbnb.model.pojo.BookingStatusType;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.Review;
import airbnb.model.repositories.BookingRepository;
import airbnb.model.repositories.PropertyRepository;
import airbnb.model.repositories.ReviewRepository;
import airbnb.model.repositories.BookingStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private BookingRepository bookingRepository;
    private ReviewRepository reviewRepository;
    private BookingStatusRepository statusRepository;
    private PropertyRepository propertyRepository;

    @Autowired
    public ReviewService(BookingRepository bookingRepository, ReviewRepository reviewRepository,
                         BookingStatusRepository statusRepository, PropertyRepository propertyRepository) {
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
        this.statusRepository = statusRepository;
        this.propertyRepository = propertyRepository;
    }

    public Review add(int userId, AddRequestReviewDTO requestReviewDTO) {
        Optional<Booking> booking = bookingRepository.findById(requestReviewDTO.getBookingId());
        if (booking.isEmpty()) {
            throw new NotFoundException("Booking with this id does not exists.");
        }
        int guestId = booking.get().getUser().getId();
        if (userId != guestId) {
            throw new BadRequestException("You cannot add review on another user's booking.");
        }
        if (booking.get().getBookingStatus().getName() != BookingStatusType.FINISHED) {
            throw new BadRequestException("You cannot add review if your booking is not finished.");
        }
        Review review = new Review(requestReviewDTO);
        review.setBooking(booking.get());
        reviewRepository.save(review);
        return review;
    }

    public Review edit(int userId, int reviewId, EditReviewDTO editReviewDTO) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isEmpty()) {
            throw new NotFoundException("Review with this id does not exists.");
        }
        int guestId = optionalReview.get().getBooking().getUser().getId();
        if (userId != guestId) {
            throw new BadRequestException("You cannot edit review on another user's booking.");
        }
        Review review = optionalReview.get();
        review.setText(editReviewDTO.getText());
        reviewRepository.save(review);
        return review;
    }

    public Review delete(int userId, int reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isEmpty()) {
            throw new NotFoundException("Review with this id does not exists.");
        }
        int guestId = review.get().getBooking().getUser().getId();
        int hostId = review.get().getBooking().getProperty().getHost().getId();
        if (userId != guestId && userId != hostId) {
            throw new BadRequestException("You are neither owner of this review nor host of this property. You cannot delete this review.");
        }
        Review deletedReview = review.get();
        reviewRepository.deleteById(reviewId);
        return deletedReview;
    }

    public List<Review> getAllReviewsForProperty(Long propertyId) {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isEmpty()) {
            throw new NotFoundException("Property with this id does not exists.");
        }
        Property property = optionalProperty.get();
        List<Review> reviews = new ArrayList<>();
        for (Booking booking : property.getBookings()) {
            reviews.add(booking.getReview());
        }
        return Collections.unmodifiableList(reviews);
    }
}
