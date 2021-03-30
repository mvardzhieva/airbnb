package airbnb.model.dto;

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
    private Long id;
    private Integer cleanliness;
    private Integer communication;
    private Integer check_in;
    private Integer accuracy;
    private Integer location;
    private Integer value;
}
