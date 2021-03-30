package airbnb.model.repositories;

import airbnb.model.pojo.Booking;
import airbnb.model.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> getAllByStatusIdAndUser(int id, User user);

    List<Booking> getAllByStatusIdIsNotAndUser(int id, User user);
}