package airbnb.model.dto;

import airbnb.model.pojo.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RatingDTO {
    private Integer cleanliness;
    private Integer communication;
    private Integer checkIn;
    private Integer accuracy;
    private Integer location;
    private Integer value;

    public RatingDTO(Rating rating) {
        this.cleanliness = rating.getCleanliness();
        this.communication = rating.getCommunication();
        this.checkIn = rating.getCheckIn();
        this.accuracy = rating.getAccuracy();
        this.location = rating.getLocation();
        this.value = rating.getValue();
    }
}
