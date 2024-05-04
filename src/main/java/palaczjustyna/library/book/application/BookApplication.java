package palaczjustyna.library.book.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.book.domain.*;

import java.util.List;

@Slf4j
@Service
public class BookApplication {

    @Autowired
    private BookService bookService;

    public List<BookDTO> getAllBooks() {
        log.info("Search all books");
        return bookService.getAllBooks();
    }

    public List<BookDTO> getBookByTitle(String title) {
        log.info("Search book by title. Title: {}", title);
        return bookService.getBookByTitle(title);
    }

    public Book findById (Integer id){
        log.info("Search book by id: {}", id);
        return bookService.findById(id);
    }

    public Book addBook(BookCreateDTO book) {
        log.info("Add book. BookCreateDTO: {}", book);
        return bookService.addBook(book);
    }

    public void deleteBook(Integer id) {
        log.info("Delete book. Book id to delete: {}", id);
        bookService.deleteBook(id);
    }

    public BookDTO updateBook(BookUpdateDTO book) {
        log.info("Update book. BookUpdateDTO: {}", book);
        return bookService.updateBook(book);
    }

    public void returnBook (Book book){
        log.info("Return book. Book: {}", book);
        bookService.returnBook(book);
    }
}
