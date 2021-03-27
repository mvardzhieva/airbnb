package airbnb.model.dto.property;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
public class DeleteRequestPropertyDTO {

    private Long id;
}
