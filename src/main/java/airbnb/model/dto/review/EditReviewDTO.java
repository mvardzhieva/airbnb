package airbnb.model.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Component
public class EditReviewDTO {
    @NotBlank(message = "Text is required.")
    private String text;
}
