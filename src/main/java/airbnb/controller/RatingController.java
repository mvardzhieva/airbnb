package airbnb.controller;

import airbnb.model.dto.rating.RatingDTO;
import airbnb.model.pojo.Rating;
import airbnb.services.interfaces.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@RestController
public class RatingController extends AbstractController {

    private RatingService ratingService;
    private SessionManager sessionManager;

    @Autowired
    public RatingController(RatingService ratingService,
                            SessionManager sessionManager) {
        this.ratingService = ratingService;
        this.sessionManager = sessionManager;
    }

    @GetMapping("users/properties/{id}/ratings")
    public List<Rating> findAllByPropertyId(@PathVariable Long id) {
        return ratingService.findAllByPropertyId(id);
    }

    @PostMapping("users/properties/{id}/ratings")
    public Rating findAvgByPropertyId(@PathVariable Long id) {
        return ratingService.findAvgByPropertyId(id);
    }

    @PostMapping("users/{userId}/properties/{propertyId}/ratings")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDTO add(@PathVariable Long userId,
                         @PathVariable Long propertyId,
                         @Valid @RequestBody RatingDTO ratingDTO,
                         HttpSession session) {

        sessionManager.validate(userId, session);
        Rating rating = ratingService.add(userId,
                propertyId,
                new Rating(ratingDTO));

        return new RatingDTO(rating);
    }

    @PutMapping("users/{userId}/properties/{propertyId}/ratings/{ratingId}")
    public RatingDTO edit(@PathVariable Long userId,
                          @PathVariable Long propertyId,
                          @PathVariable Long ratingId,
                          @RequestBody RatingDTO ratingDTO,
                          HttpSession session) {

        sessionManager.validate(userId, session);
        Rating rating = ratingService.edit(userId, ratingId, propertyId,
                new Rating(ratingDTO));

        return new RatingDTO(rating);
    }

    @DeleteMapping("users/{userId}/properties/{propertyId}/ratings/{ratingId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId,
                       @PathVariable Long propertyId,
                       @PathVariable Long ratingId,
                       HttpSession session) {

        sessionManager.validate(userId, session);
        ratingService.delete(userId, ratingId, propertyId);
    }
}
