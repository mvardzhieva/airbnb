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
import java.util.List;
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

    @ManyToOne
    @JoinColumn(name="host_id")
    @JsonBackReference
    private User host;

//    @OneToOne
//    @JoinColumn(name = "property_types", referencedColumnName = "id")
    private Long typeId;

    @OneToOne
    @JoinColumn(name = "cities", referencedColumnName = "id")
    private City cityId;

    @OneToOne
    @JoinColumn(name = "countries", referencedColumnName = "id")
    private Country countryId;

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
    private Double rating;
    private Boolean isFree;

    @OneToMany(mappedBy = "property")
    @JsonManagedReference
    private List<Booking> bookings;

    public Property(AddRequestPropertyDTO propertyDTO) {
        this.typeId = propertyDTO.getTypeId();
        this.cityId = propertyDTO.getCity();
        this.countryId = propertyDTO.getCountry();
        this.latitude = propertyDTO.getLatitude();
        this.longitude = propertyDTO.getLongitude();
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
                ", typeId=" + typeId +
                ", cityId=" + cityId +
                ", countryId=" + countryId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", rating=" + rating +
                ", isFree=" + isFree +
                ", media=" + media +
                ", host=" + host +
                '}';
    }
}
