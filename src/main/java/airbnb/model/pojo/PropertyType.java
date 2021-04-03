package airbnb.model.pojo;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "property_types")
public class PropertyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "PropertyType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
