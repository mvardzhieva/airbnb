package airbnb.model.pojo;

import airbnb.model.dto.property.AddRequestPropertyDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Component
@Getter
@Setter
@NoArgsConstructor
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long type_id;
    private Long host_id;
    private Long location_id;
    private String name;
    private String description;
    private Double price;
    private LocalDateTime created_at;
    private Double rating;
    private Boolean is_free;

    public Property(AddRequestPropertyDTO propertyDTO) {
        this.type_id = propertyDTO.getType_id();
        this.host_id = propertyDTO.getHost_id();
        this.location_id = propertyDTO.getLocation_id();
        this.name = propertyDTO.getName();
        this.description = propertyDTO.getDescription();
        this.price = propertyDTO.getPrice();
        this.created_at = LocalDateTime.now();
        this.rating = 0.0;
        this.is_free = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return id.equals(property.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", type_id=" + type_id +
                ", host_id=" + host_id +
                ", location_id=" + location_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", created_at=" + created_at +
                ", rating=" + rating +
                ", is_free=" + is_free +
                '}';
    }
}
