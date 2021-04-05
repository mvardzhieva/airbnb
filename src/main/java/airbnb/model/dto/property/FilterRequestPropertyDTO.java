package airbnb.model.dto.property;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Component
@Data
public class FilterRequestPropertyDTO {

    @Positive
    private Long typeId;

    @Positive
    private Long cityId;

    @Positive
    private Long countryId;

    private String name;

    private String description;

    @Positive
    private Double minPrice;

    @Positive
    private Double maxPrice;
}
