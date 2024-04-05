package palaczjustyna.library.book.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.book.domain.BookCreateDTO;
import palaczjustyna.library.book.domain.BookService;
import palaczjustyna.library.book.domain.BookUpdateDTO;

import java.util.List;

@Service
public class BookApplication {

    @Autowired
    private BookService bookService;

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public List<Book> getBookByTitle(String title) {
        return bookService.getBookByTitle(title);
    }

    public Book addBook(BookCreateDTO book) {
        return bookService.addBook(book);
    }

    public void deleteBook(Integer id) {
        bookService.deleteBook(id);
    }

    public Book updateBook(BookUpdateDTO book) {
        return bookService.updateBook(book);
    }
}
