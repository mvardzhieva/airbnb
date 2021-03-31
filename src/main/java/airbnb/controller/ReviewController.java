package airbnb.controller;

import airbnb.exceptions.BadRequestException;
import airbnb.model.dto.review.AddRequestReviewDTO;
import airbnb.model.dto.review.EditReviewDTO;
import airbnb.model.pojo.Review;
import airbnb.model.pojo.User;
import airbnb.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ReviewController extends AbstractController {
    private ReviewService reviewService;
    private SessionManager sessionManager;

    @Autowired
    public ReviewController(ReviewService reviewService, SessionManager sessionManager) {
        this.reviewService = reviewService;
        this.sessionManager = sessionManager;
    }

    @PutMapping("/users/{id}/properties/reviews")
    public Review add(@PathVariable int id, @RequestBody AddRequestReviewDTO requestReviewDTO, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != id) {
            throw new BadRequestException("You cannot add review on behalf of another user.");
        }
        return reviewService.add(id, requestReviewDTO);
    }

    @PostMapping("/users/{user_id}/properties/reviews/{review_id}")
    public Review edit(@PathVariable(name = "user_id") int userId, @PathVariable(name = "review_id") int reviewId,
                       @RequestBody EditReviewDTO editReviewDTO, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != userId) {
            throw new BadRequestException("You cannot edit another user's review.");
        }
        return reviewService.edit(userId, reviewId, editReviewDTO);
    }

    @DeleteMapping("/users/{user_id}/properties/reviews/{review_id}")
    public Review delete(@PathVariable(name = "user_id") int userId,
                         @PathVariable(name = "review_id") int reviewId, HttpSession session) {
        User user = sessionManager.getLoggedUser(session);
        if (user.getId() != userId) {
            throw new BadRequestException("You cannot delete another user's review.");
        }
        return reviewService.delete(userId, reviewId);
    }

    @GetMapping("/properties/{id}/reviews")
    public List<Review> getAllByPropertyId(@PathVariable Long id) {
        return reviewService.getAllReviewsForProperty(id);
    }
}
