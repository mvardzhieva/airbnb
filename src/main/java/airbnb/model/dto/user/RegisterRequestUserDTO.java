package airbnb.model.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Component
public class RegisterRequestUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmedPassword;
    private String phoneNumber;
    private LocalDate dateOfBirth;
}
