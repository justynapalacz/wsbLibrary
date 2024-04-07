package palaczjustyna.library.borrow.infrastructure;

import org.springframework.data.repository.CrudRepository;
import palaczjustyna.library.borrow.domain.Borrow;

public interface BorrowRepository extends CrudRepository<Borrow, Integer> {
}
