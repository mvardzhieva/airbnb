package airbnb.model.pojo;

import airbnb.util.BookingStatusTypeConverter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "booking_statuses")
public class BookingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Convert(converter = BookingStatusTypeConverter.class)
    private BookingStatusType name;

    @OneToMany(mappedBy = "bookingStatus")
    @JsonManagedReference
    private List<Booking> bookings;

}
