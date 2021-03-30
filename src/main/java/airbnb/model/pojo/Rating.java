package airbnb.model.pojo;

import airbnb.model.dto.RatingDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cleanliness;
    private Integer communication;
    private Integer checkIn;
    private Integer accuracy;
    private Integer location;
    private Integer value;

    public Rating(RatingDTO ratingDTO) {
        this.cleanliness = ratingDTO.getCleanliness();
        this.communication = ratingDTO.getCommunication();
        this.checkIn = ratingDTO.getCheckIn();
        this.accuracy = ratingDTO.getAccuracy();
        this.location = ratingDTO.getLocation();
        this.value = ratingDTO.getLocation();
    }
}
