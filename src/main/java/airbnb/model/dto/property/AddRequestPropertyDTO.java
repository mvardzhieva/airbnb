package airbnb.model.dto.property;


import airbnb.model.pojo.City;
import airbnb.model.pojo.Country;
import airbnb.model.pojo.PropertyType;
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
    private PropertyType type;
    private City city;
    private Country country;
    private Double latitude;
    private Double longitude;
    private String name;
    private String description;
    private Double price;

    @Override
    public String toString() {
        return "AddRequestPropertyDTO{" +
                "hostId=" + hostId +
                ", type=" + type +
                ", city=" + city +
                ", country=" + country +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
