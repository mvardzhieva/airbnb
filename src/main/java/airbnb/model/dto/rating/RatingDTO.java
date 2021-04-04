package airbnb.model.dto.rating;

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

    private Float cleanliness;
    private Float communication;
    private Float checkIn;
    private Float accuracy;
    private Float location;
    private Float value;

    public RatingDTO(Rating rating) {
        this.cleanliness = rating.getCleanliness();
        this.communication = rating.getCommunication();
        this.checkIn = rating.getCheckIn();
        this.accuracy = rating.getAccuracy();
        this.location = rating.getLocation();
        this.value = rating.getValue();
    }
}
