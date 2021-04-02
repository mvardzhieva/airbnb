package airbnb.model.repositories;

import airbnb.model.pojo.Property;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {



    // TODO IMPROVE
    @Query(value = "Select p.* FROM properties p " +
            "WHERE ( 6371 * acos(cos(radians(37)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - " +
            "radians(-122)) + sin(radians(37)) * sin(radians(p.latitude)))) " +
            "< ?;", nativeQuery = true)
    Collection<Property> findNearby(Integer proximity);

//    @Query(value = "FROM Property WHERE PropertyType.id = ?1 " +
//            "AND price > ?2  " +
//            "AND price < ?3" +
//            "AND City.id = ?4" +
//            "AND Country.id = ?5" +
//            "AND description.equals(?6)" +
//            "AND isFree = true ")
//    Collection<Property> filterBy(Long typeId,
//                                  Double minPrice,
//                                  Double maxPrice,
//                                  Long cityId,
//                                  Long countryId
//                                  String description
//                                  );

}
