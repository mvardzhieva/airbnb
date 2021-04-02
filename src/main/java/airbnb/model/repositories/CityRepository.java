package airbnb.model.repositories;

import airbnb.model.pojo.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
