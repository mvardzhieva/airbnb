package airbnb.model.pojo;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;


@Entity
@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
