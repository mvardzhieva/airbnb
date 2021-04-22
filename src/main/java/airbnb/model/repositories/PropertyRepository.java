package airbnb.model.repositories;

import airbnb.model.pojo.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Haversine formula -  the shortest distance over the earth’s surface.

    @Query(value = "Select p.* FROM properties p " +
            "WHERE ( 6371 * acos(cos(radians(?1)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - " +
            "radians(?2)) + sin(radians(?1)) * sin(radians(p.latitude)))) " +
            "< ?3", nativeQuery = true)
    Collection<Property> findNearby(Double latitude, Double longitude, Float proximity);

    List<Property> findAllByHostId(Integer userId);
}
