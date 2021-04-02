package airbnb.model.repositories;

import airbnb.model.pojo.Property;
import airbnb.model.pojo.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {


    //TODO REFACTOR TO NATIVE
    List<Rating> findAllByProperty(Property property);
}
