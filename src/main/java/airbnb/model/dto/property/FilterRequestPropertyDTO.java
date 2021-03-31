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
    private Double minPrice;
    private Double maxPrice;
    private Integer proximity;
    private String description;
    private Double rating;

    //Todo Hashcode and Equals
}
