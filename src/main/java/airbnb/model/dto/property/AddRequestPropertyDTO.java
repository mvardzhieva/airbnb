package airbnb.model.dto.property;


import airbnb.model.pojo.City;
import airbnb.model.pojo.Country;
import airbnb.model.pojo.PropertyType;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddRequestPropertyDTO {

    @NotNull
    private PropertyType type;

    @NotNull
    private City city;

    @NotNull
    private Country country;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

}
