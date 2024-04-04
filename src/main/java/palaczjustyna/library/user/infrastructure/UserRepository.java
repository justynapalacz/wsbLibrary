package palaczjustyna.library.user.infrastructure;

import org.springframework.data.repository.CrudRepository;
import palaczjustyna.library.user.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
