package airbnb.model.dto.property;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Component
@Data
public class FilterRequestPropertyDTO {

    @NotNull
    @Positive
    private Long typeId;

    @NotNull
    @Positive
    private Long cityId;

    @NotNull
    @Positive
    private Long countryId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    @Positive
    private Double minPrice;

    @NotNull
    @Positive
    private Double maxPrice;
}
