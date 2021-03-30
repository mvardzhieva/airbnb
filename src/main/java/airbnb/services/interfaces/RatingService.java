package airbnb.services.interfaces;

import airbnb.model.dto.RatingDTO;

public interface RatingService {

    RatingDTO add(RatingDTO ratingDTO);

    RatingDTO edit(RatingDTO ratingDTO);

    void delete(Long ratingId);
}
