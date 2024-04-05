package palaczjustyna.library.book.infrastructure;

import org.springframework.data.repository.CrudRepository;
import palaczjustyna.library.book.domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findByTitle (String title);
}
