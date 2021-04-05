package airbnb.model.dto.rating;

import airbnb.model.pojo.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RatingDTO {

    @NotNull
    @DecimalMin(value = "1.0", message = "Minimum allowed value is 1.")
    @DecimalMax(value = "5.0", message = "Maximum allowed value is 5.")
    private Float cleanliness;

    @NotNull
    @DecimalMin(value = "1.0", message = "Minimum allowed value is 1.")
    @DecimalMax(value = "5.0", message = "Maximum allowed value is 5.")
    private Float communication;

    @NotNull
    @DecimalMin(value = "1.0", message = "Minimum allowed value is 1.")
    @DecimalMax(value = "5.0", message = "Maximum allowed value is 5.")
    private Float checkIn;

    @NotNull
    @DecimalMin(value = "1.0", message = "Minimum allowed value is 1.")
    @DecimalMax(value = "5.0", message = "Maximum allowed value is 5.")
    private Float accuracy;

    @NotNull
    @DecimalMin(value = "1.0", message = "Minimum allowed value is 1.")
    @DecimalMax(value = "5.0", message = "Maximum allowed value is 5.")
    private Float location;

    @NotNull
    @DecimalMin(value = "1.0", message = "Minimum allowed value is 1.")
    @DecimalMax(value = "5.0", message = "Maximum allowed value is 5.")
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
