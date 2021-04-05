package airbnb.services.interfaces;

import airbnb.model.dto.review.AddRequestReviewDTO;
import airbnb.model.dto.review.EditReviewDTO;
import airbnb.model.pojo.Review;

import java.util.List;

public interface ReviewService {
    Review add(int userId, AddRequestReviewDTO requestReviewDTO);

    Review edit(int userId, int reviewId, EditReviewDTO editReviewDTO);

    Review delete(int userId, int reviewId);

    List<Review> getAllReviewsForProperty(Long propertyId);
}
