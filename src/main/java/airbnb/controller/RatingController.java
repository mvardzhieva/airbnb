package airbnb.controller;

import airbnb.model.dto.RatingDTO;
import airbnb.model.pojo.Rating;
import airbnb.services.interfaces.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class RatingController {

    private RatingService ratingService;

    //TODO VALIDATE

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/rating")
    public RatingDTO add(@RequestBody RatingDTO ratingDTO, HttpSession session) {
        ratingService.add(new Rating(ratingDTO));
        return null;
    }
    @PutMapping("/rating")
    public RatingDTO edit(@RequestBody RatingDTO ratingDTO, HttpSession session) {
        ratingService.edit(new Rating(ratingDTO));
        return null;
    }

    @DeleteMapping("/rating/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, HttpSession session) {
        ratingService.delete(id);
    }
}
