package airbnb.model.dto.property;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
public class FilterRequestPropertyDTO {

    private Long typeId;
    private Long cityId;
    private Long countryId;
    private Double latitude;
    private Double longitude;
    private String name;
    private String description;
    private Double price;
    private Double rating;
    private Boolean isFree;

    //Todo Hashcode and Equals
}
