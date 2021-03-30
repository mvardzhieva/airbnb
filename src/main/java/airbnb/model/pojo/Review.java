package airbnb.model.pojo;

import airbnb.model.dto.review.AddRequestReviewDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    @JsonBackReference
    private Booking booking;
    private String text;
    private LocalDateTime createdAt;

    public Review(AddRequestReviewDTO requestReviewDTO) {
        this.text = requestReviewDTO.getText();
        this.createdAt = LocalDateTime.now();
    }
}
