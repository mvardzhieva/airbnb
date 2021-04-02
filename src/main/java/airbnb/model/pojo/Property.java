package airbnb.model.pojo;

import airbnb.model.dto.property.AddRequestPropertyDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="host_id")
    @JsonBackReference
    private User host;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PropertyType type;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @JsonManagedReference
    @OneToMany(mappedBy = "property")
//    CascadeType.All
    private Set<Media> media;

    private Double latitude;
    private Double longitude;
    private String name;
    private String description;
    private Double price;
    private LocalDate createdAt;

    @OneToMany(mappedBy = "property")
    @JsonManagedReference
    private List<Rating> ratings;

    @OneToMany(mappedBy = "property")
    @JsonManagedReference
    private List<Booking> bookings;

    public Property(AddRequestPropertyDTO propertyDTO) {
        this.type = propertyDTO.getType();
        this.city = propertyDTO.getCity();
        this.country = propertyDTO.getCountry();
        this.latitude = propertyDTO.getLatitude();
        this.longitude = propertyDTO.getLongitude();
        this.name = propertyDTO.getName();
        this.description = propertyDTO.getDescription();
        this.price = propertyDTO.getPrice();
        this.createdAt = LocalDate.now();
        this.ratings = null;
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
}
