package palaczjustyna.library.book.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.book.domain.BookService;

import java.util.List;

@Service
public class BookApplication {

    @Autowired
    private BookService bookService;

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
