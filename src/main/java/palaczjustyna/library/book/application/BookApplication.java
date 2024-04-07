package palaczjustyna.library.book.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.book.domain.*;

import java.util.List;

@Service
public class BookApplication {

    @Autowired
    private BookService bookService;

    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    public List<BookDTO> getBookByTitle(String title) {
        return bookService.getBookByTitle(title);
    }

    public Book findById (Integer id){
        return bookService.findById(id);
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
