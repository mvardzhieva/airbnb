package airbnb.services.interfaces;

import airbnb.model.dto.RatingDTO;
import airbnb.model.pojo.Rating;

public interface RatingService {

    Rating add(Rating rating);

    Rating edit(Rating rating);

    void delete(Long ratingId);
}
