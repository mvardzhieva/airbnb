package airbnb.model.dto.user;

import airbnb.model.pojo.Booking;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Component
public class RegisterResponseUserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private List<Property> properties;
    private List<Booking> bookings;

    public RegisterResponseUserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.dateOfBirth = user.getDateOfBirth();
        this.createdAt = user.getCreatedAt();
        this.properties = user.getProperties();
        this.bookings = user.getBookings();
    }
}
