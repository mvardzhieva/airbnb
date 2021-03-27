package airbnb.model.dto.property;


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

    private Long type_id;
    private Long host_id;
    private Long location_id;
    private String name;
    private String description;
    private Double price;

    @Override
    public String toString() {
        return "AddRequestPropertyDTO{" +
                "type_id=" + type_id +
                ", host_id=" + host_id +
                ", location_id=" + location_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
