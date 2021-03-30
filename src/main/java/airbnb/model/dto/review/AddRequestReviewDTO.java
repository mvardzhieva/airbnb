package airbnb.model.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class AddRequestReviewDTO {
    private Long bookingId;
    private String text;
}
