package palaczjustyna.library.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import palaczjustyna.library.book.application.BookApplication;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.book.domain.BookCreateDTO;
import palaczjustyna.library.book.domain.BookUpdateDTO;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookApplication bookApplication;

    @GetMapping("/getBooks")
    List<Book> getAllBooks() {
        return bookApplication.getAllBooks();
    }

    @GetMapping("/getBookByTitle")
    List<Book> getBookByTitle(@RequestParam(value = "title")  String title){
        return bookApplication.getBookByTitle(title);
    }

    @PostMapping("/addBook")
    Book addBook(@RequestBody BookCreateDTO book){
        return bookApplication.addBook(book);
    }

    @DeleteMapping("/deleteBook")
    void deleteBook (@RequestParam(value = "id")  Integer id){
        bookApplication.deleteBook(id);
    }

    @PutMapping ("/updateBook")
    Book updateBook(@RequestBody BookUpdateDTO book){
        return bookApplication.updateBook (book);
    }
}
