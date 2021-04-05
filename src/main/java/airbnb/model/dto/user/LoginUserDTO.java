package airbnb.model.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Component
public class LoginUserDTO {
    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email.")
    @Size(max = 100, message = "Email is required and cannot exceed 100 characters.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;
}
