package airbnb.controller;

import airbnb.model.dto.RatingDTO;
import airbnb.model.pojo.Rating;
import airbnb.services.RatingService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {

    private RatingService ratingService;

    @PutMapping("/rating")
    public RatingDTO add(@RequestBody RatingDTO ratingDTO) {
        return ratingService.add(ratingDTO);
    }
}
