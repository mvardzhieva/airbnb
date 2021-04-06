package airbnb.services.interfaces;


import airbnb.model.pojo.Media;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


public interface MediaService {

    Media upload(Long id, MultipartFile file);

    byte[] download(Long id);

    List<Media> getAllByPropertyId(Long id);

    List<Media> getAll();

    void deleteOneByMediaId(Long propertyId, Long mediaId);

    void deleteAllByPropertyId(Long userId, Long propertyId);

    Media findById(Long id);
}
