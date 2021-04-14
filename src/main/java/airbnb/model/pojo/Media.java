package airbnb.model.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Data
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String mimeType;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

}

