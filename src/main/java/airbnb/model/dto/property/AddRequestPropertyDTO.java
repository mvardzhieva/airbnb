package airbnb.model.dto.property;


import airbnb.model.pojo.City;
import airbnb.model.pojo.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddRequestPropertyDTO {

    private Long hostId;
    private Long typeId;
    private City city;
    private Country country;
    private Double latitude;
    private Double longitude;
    private String name;
    private String description;
    private Double price;

}
