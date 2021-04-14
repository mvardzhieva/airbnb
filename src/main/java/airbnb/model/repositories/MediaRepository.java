package airbnb.model.repositories;

import airbnb.model.pojo.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Media> findAllByPropertyId(Long id);
}