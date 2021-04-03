package airbnb.controller;

import airbnb.model.dto.RatingDTO;
import airbnb.model.pojo.Rating;
import airbnb.services.interfaces.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
public class RatingController extends AbstractController {

    private RatingService ratingService;
    private SessionManager sessionManager;

    //TODO VALIDATE

    @Autowired
    public RatingController(RatingService ratingService,
                            SessionManager sessionManager) {
        this.ratingService = ratingService;
        this.sessionManager = sessionManager;
    }

    @GetMapping("users/properties/{propertyId}/ratings")
    public List<Rating> findAllByPropertyId(@PathVariable Long propertyId) {
        return ratingService.findAllByPropertyId(propertyId);
    }

    @PostMapping("users/properties/{propertyId}/ratings")
    public Rating findAvgByPropertyId(@PathVariable Long propertyId) {
        return ratingService.findAvgByPropertyId(propertyId);
    }

    @PutMapping("users/{userId}/properties/{propertyId}/ratings")
    public RatingDTO add(@PathVariable Long userId,
                         @PathVariable Long propertyId,
                         @RequestBody RatingDTO ratingDTO,
                         HttpSession session) {

        sessionManager.validate(userId, session);
        Rating rating = ratingService.add(userId,
                propertyId,
                new Rating(ratingDTO));

        return new RatingDTO(rating);
    }

    @PostMapping("users/{userId}/properties/{propertyId}/ratings/{ratingId}")
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
