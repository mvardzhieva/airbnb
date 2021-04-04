package airbnb.services;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.lang.annotation.Documented;

@Data
public class AmazonImage {


    @Id
    private String amazonUserImageId;

    @NotNull
    private String imageUrl;

}
