package palaczjustyna.library.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import palaczjustyna.library.book.application.BookApplication;
import palaczjustyna.library.book.domain.Book;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookApplication bookApplication;

    @GetMapping("/getBooks")
    List<Book> getAllBooks() {
        return bookApplication.getAllBooks();
    }

}
