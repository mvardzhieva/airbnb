package airbnb.model.repositories;

import airbnb.model.pojo.BookingStatus;
import airbnb.model.pojo.BookingStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingStatusRepository extends JpaRepository<BookingStatus, Integer> {
    BookingStatus findByName(BookingStatusType bookingStatusType);
}
