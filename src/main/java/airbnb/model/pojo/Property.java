package airbnb.model.pojo;

import airbnb.model.dto.property.AddRequestPropertyDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;;
import java.util.Set;

@Entity
@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode
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

    private String name;
    private String description;
    private BigDecimal price;
    private Double latitude;
    private Double longitude;
    private LocalDate createdAt;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "property")
    private Set<Media> media;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "property")
    @JsonManagedReference
    private List<Rating> ratings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "property")
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

}
