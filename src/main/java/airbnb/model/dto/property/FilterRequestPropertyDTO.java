package airbnb.model.dto.property;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

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
    private BigDecimal minPrice;

    @Positive
    private BigDecimal maxPrice;
}
