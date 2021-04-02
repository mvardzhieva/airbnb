package airbnb.model.pojo;

import airbnb.model.dto.booking.AddRequestBookingDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonBackReference
    private Property property;
    private int statusId;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToOne(mappedBy = "booking")
    @JsonManagedReference
    private Review review;

    @OneToMany(
            mappedBy = "property",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Rating> rating;

    public Booking(AddRequestBookingDTO requestBookingDTO) {
        this.startDate = requestBookingDTO.getStartDate();
        this.endDate = requestBookingDTO.getEndDate();
    }

}
