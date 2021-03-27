package airbnb.model.dto.property;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
public class FilterRequestPropertyDTO {

    private Long type_id;
    private Long host_id;
    private Long location_id;
    private String name;
    private String description;
    private Double price;
    private Double rating;
    private Boolean is_free;
}
