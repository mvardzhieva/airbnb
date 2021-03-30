package airbnb.model.repositories;

import airbnb.model.pojo.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<BookingStatus, Integer> {
    BookingStatus findByName(String name);
}
