package airbnb.model.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@Component
public class AddRequestReviewDTO {
    @Positive(message = "Booking Id should be positive number.")
    private Long bookingId;

    @NotBlank(message = "Text is required.")
    private String text;
}
