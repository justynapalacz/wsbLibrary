package palaczjustyna.library.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import palaczjustyna.library.book.application.BookApplication;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.book.domain.BookCreateDTO;
import palaczjustyna.library.book.domain.BookDTO;
import palaczjustyna.library.book.domain.BookUpdateDTO;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookApplication bookApplication;

    @GetMapping("/getBooks")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    @ResponseStatus(HttpStatus.OK)
    List<BookDTO> getAllBooks() {
        return bookApplication.getAllBooks();
    }

    @GetMapping("/getBookByTitle")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    @ResponseStatus(HttpStatus.OK)
    List<BookDTO> getBookByTitle(@RequestParam(value = "title")  String title){
        return bookApplication.getBookByTitle(title);
    }

    @PostMapping("/addBook")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    Book addBook(@RequestBody BookCreateDTO book){
        return bookApplication.addBook(book);
    }

    @DeleteMapping("/deleteBook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBook (@RequestParam(value = "id")  Integer id){
        bookApplication.deleteBook(id);
    }

    @PutMapping ("/updateBook")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    BookDTO updateBook(@RequestBody BookUpdateDTO book){
        return bookApplication.updateBook (book);
    }
}
