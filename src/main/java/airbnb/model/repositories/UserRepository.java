package airbnb.model.repositories;

import airbnb.model.pojo.User;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Scope(scopeName = "session")
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
