package airbnb.model.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Component
public class AddRequestBookingDTO {
    @Positive(message = "Property Id should be positive number.")
    private Long propertyId;

    @NotNull(message = "Start date is required.")
    @FutureOrPresent(message = "You have entered invalid start date.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date is required.")
    @Future(message = "You have entered invalid end date.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
