package airbnb.model.repositories;

import airbnb.model.pojo.Media;
import org.springframework.data.repository.CrudRepository;

public interface MediaRepository extends CrudRepository<Media, Long> {
}
