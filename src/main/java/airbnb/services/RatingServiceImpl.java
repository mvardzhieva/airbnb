package airbnb.services;

import airbnb.model.dto.RatingDTO;
import airbnb.model.repositories.RatingRepository;
import airbnb.services.interfaces.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    // TODO CONNECT WITH BOOKINGS
    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

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
