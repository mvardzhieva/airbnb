package airbnb.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Component
public class RegisterRequestUserDTO {
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

    @NotBlank(message = "Confirmed password is required.")
    private String confirmedPassword;

    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;

    @NotNull(message = "Date of birth is required.")
    @Past(message = "You have entered invalid date of birth.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

}
