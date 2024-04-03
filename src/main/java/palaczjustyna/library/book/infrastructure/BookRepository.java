package palaczjustyna.library.book.infrastructure;

import org.springframework.data.repository.CrudRepository;
import palaczjustyna.library.book.domain.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
