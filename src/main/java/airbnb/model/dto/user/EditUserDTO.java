package airbnb.model.dto.user;

import airbnb.model.pojo.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Component
public class EditUserDTO {
    @NotBlank(message = "First name is required.")
    @Size(min = 1, max = 100, message = "First name is required and cannot exceed 100 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 1, max = 100, message = "Last name is required and cannot exceed 100 characters.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email.")
    @Size(max = 100, message = "Email is required and cannot exceed 100 characters.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;

    @NotNull(message = "Date of birth is required.")
    @Past(message = "You have entered invalid date of birth.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Size(max = 100, message = "Address cannot exceed 100 characters.")
    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Size(max = 20, message = "Government Id cannot exceed 20 characters.")
    private String governmentId;
}
