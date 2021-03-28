package airbnb.service;


import airbnb.model.pojo.Media;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    Media add(Long id, MultipartFile file);

    void filter();

    void delete(Long id);


}
