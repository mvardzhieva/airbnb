package airbnb.service;


import airbnb.model.pojo.Media;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface MediaService {

    Media add(Long id, MultipartFile file);

    void filter();

    void deleteById(Long id);

    Set<Media> getAllByPropertyId(Long id);

    void deleteByPropertyId(Long id);
}
