package palaczjustyna.library.book.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    public List<Book> getAllBooks() {
        Book book = new Book();
        book.setTitle("java master");

        return List.of(book);
    }
}
