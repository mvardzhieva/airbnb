package airbnb.model.dto.booking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Component
public class AddRequestBookingDTO {
    private Long propertyId;
    private LocalDate startDate;
    private LocalDate endDate;
}
