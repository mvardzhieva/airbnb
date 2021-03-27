package airbnb.model.dto;

import airbnb.model.pojo.Gender;
import airbnb.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserProfileDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private Gender gender;
    private String governmentId;
    private LocalDateTime createdAt;


    public UserProfileDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.dateOfBirth = user.getDateOfBirth();
        this.address = user.getAddress();
        this.gender = user.getGender();
        this.governmentId = user.getGovernmentId();
        this.createdAt = user.getCreatedAt();

    }
}