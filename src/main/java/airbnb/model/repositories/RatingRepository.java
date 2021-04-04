package airbnb.model.repositories;

import airbnb.model.pojo.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findAllByPropertyId(Long id);

}
