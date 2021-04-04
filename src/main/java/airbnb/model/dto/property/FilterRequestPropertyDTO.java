package airbnb.model.dto.property;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class FilterRequestPropertyDTO {

    private Long typeId;
    private Long cityId;
    private Long countryId;
    private String name;
    private String description;
    private Double minPrice;
    private Double maxPrice;
}
