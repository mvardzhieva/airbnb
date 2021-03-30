package airbnb.services;

import airbnb.model.dto.RatingDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public class RatingServiceImpl implements RatingService {

    @Override
    public RatingDTO add(RatingDTO ratingDTO) {
        return null;
    }

    @Override
    public RatingDTO edit(RatingDTO ratingDTO) {
        return null;
    }

    @Override
    public void delete(Long ratingId) {

    }

}
