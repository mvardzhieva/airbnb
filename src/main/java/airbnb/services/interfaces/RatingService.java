package airbnb.services.interfaces;

import airbnb.model.pojo.Rating;

import java.util.List;

public interface RatingService {

    Rating add(Long userId, Long propertyId, Rating rating);

    Rating edit(Long userId, Long ratingId, Long propertyId, Rating rating);

    void delete(Long userId, Long ratingId, Long propertyId);

    List<Rating> findAllByPropertyId(Long propertyId);

    Rating findAvgByPropertyId(Long propertyId);
}
