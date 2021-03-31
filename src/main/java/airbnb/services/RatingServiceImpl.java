package airbnb.services;


import airbnb.model.pojo.Rating;
import airbnb.model.repositories.RatingRepository;
import airbnb.services.interfaces.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;


    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating add(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating edit(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public void delete(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }
}
