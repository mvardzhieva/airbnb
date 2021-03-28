package airbnb.model.repositories;

import airbnb.model.pojo.Property;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {
//
//    @Query("TRUNCATE TABLE properties")
//    @Query
//    @Modifying("TRUNCATE TABLE properties")
//    void  truncate();


}
