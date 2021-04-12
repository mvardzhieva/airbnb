package airbnb.model.repositories;

import airbnb.model.pojo.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MediaRepository extends JpaRepository<Media, Long> {

    Set<Media> findAllByPropertyId(Long id);
}
