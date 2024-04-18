package palaczjustyna.library.user.infrastructure;

import org.springframework.data.repository.CrudRepository;
import palaczjustyna.library.user.domain.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByLastNameLike(String lastName);
}
