package airbnb.model.pojo;

import airbnb.util.GenderConverter;
import airbnb.model.dto.user.RegisterRequestUserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;

    @Convert(converter = GenderConverter.class)
    private Gender gender;
    private String governmentId;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "host")
//    @JsonManagedReference
//    @JsonIgnore
    private List<Property> properties;

    public User(RegisterRequestUserDTO requestUserDTO) {
        this.firstName = requestUserDTO.getFirstName();
        this.lastName = requestUserDTO.getLastName();
        this.email = requestUserDTO.getEmail();
        this.password = requestUserDTO.getPassword();
        this.phoneNumber = requestUserDTO.getPhoneNumber();
        this.dateOfBirth = requestUserDTO.getDateOfBirth();
        this.properties = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

}
