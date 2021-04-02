package airbnb.model.repositories;

import airbnb.model.pojo.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
