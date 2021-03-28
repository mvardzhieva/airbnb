package airbnb.model.pojo;

import airbnb.model.dto.property.AddRequestPropertyDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

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
    private Long typeId;
    private Long locationId;
    private String name;
    private String description;
    private Double price;
    private LocalDate createdAt;
    private Double rating;
    private Boolean isFree;

    @JsonManagedReference
    @OneToMany(mappedBy = "property")
    private Set<Media> media;

    @ManyToOne
    @JoinColumn(name="host_id")
    @JsonBackReference
    private User host;

    public Property(AddRequestPropertyDTO propertyDTO) {
        this.typeId = propertyDTO.getType_id();
        this.locationId = propertyDTO.getLocation_id();
        this.name = propertyDTO.getName();
        this.description = propertyDTO.getDescription();
        this.price = propertyDTO.getPrice();
        this.createdAt = LocalDate.now();
        this.rating = 0.0;
        this.isFree = true;
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
                ", type_id=" + typeId +
                ", location_id=" + locationId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", created_at=" + createdAt +
                ", rating=" + rating +
                ", is_free=" + isFree +
                '}';
    }
}
