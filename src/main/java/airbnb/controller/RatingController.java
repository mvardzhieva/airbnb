package airbnb.controller;

import airbnb.model.dto.RatingDTO;
import airbnb.services.interfaces.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RatingController {

    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/rating")
    public RatingDTO add(@RequestBody RatingDTO ratingDTO) {
        return ratingService.add(ratingDTO);
    }
    @PutMapping("/rating")
    public RatingDTO edit(@RequestBody RatingDTO ratingDTO) {
        return ratingService.edit(ratingDTO);
    }

    @DeleteMapping("/rating/{id}")
    public void delete(@PathVariable Long id) {
        ratingService.delete(id);
    }
}
