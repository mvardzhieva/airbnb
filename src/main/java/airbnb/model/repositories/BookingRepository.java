package airbnb.model.repositories;

import airbnb.model.pojo.Booking;
import airbnb.model.pojo.BookingStatus;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> getAllByBookingStatusAndUser(BookingStatus bookingStatus, User user);

    List<Booking> getAllByBookingStatusIsNot(BookingStatus bookingStatus);

    Booking findByUserAndProperty(User user, Property property);

//    Booking getFirstByPropertyOrderByEndDateEndDateDesc(Property property);
}
