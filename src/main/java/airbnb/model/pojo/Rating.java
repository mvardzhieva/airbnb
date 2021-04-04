package airbnb.model.pojo;

import airbnb.model.dto.rating.RatingDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float cleanliness;
    private Float communication;
    private Float checkIn;
    private Float accuracy;
    private Float location;
    private Float value;

    @ManyToOne
    @JsonBackReference
    private Property property;

    public Rating(RatingDTO ratingDTO) {
        this.cleanliness = ratingDTO.getCleanliness();
        this.communication = ratingDTO.getCommunication();
        this.checkIn = ratingDTO.getCheckIn();
        this.accuracy = ratingDTO.getAccuracy();
        this.location = ratingDTO.getLocation();
        this.value = ratingDTO.getLocation();
    }

    public void update(Rating rating) {
        this.cleanliness = rating.getCleanliness();
        this.communication = rating.getCommunication();
        this.checkIn = rating.getCheckIn();
        this.accuracy = rating.getAccuracy();
        this.location = rating.getLocation();
        this.value = rating.getLocation();
    }
}
