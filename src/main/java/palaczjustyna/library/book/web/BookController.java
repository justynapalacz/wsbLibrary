package palaczjustyna.library.book.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
public class BookController {

    @Autowired
    private BookApplication bookApplication;

    @GetMapping("/books")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    @ResponseStatus(HttpStatus.OK)
    List<BookDTO> getAllBooks() {
        log.info("Search all books");
        return bookApplication.getAllBooks();
    }

    @GetMapping("/booksByTitle")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    @ResponseStatus(HttpStatus.OK)
    List<BookDTO> getBookByTitle(@RequestParam(value = "title")  String title){
        log.info("Search book by title. Title: {}" ,title);
        return bookApplication.getBookByTitle(title);
    }

    @PostMapping("/books")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    Book addBook(@Valid @RequestBody BookCreateDTO book){
        log.info("Add book. BookCreateDTO: {}", book);
        return bookApplication.addBook(book);
    }

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBook (@PathVariable(value = "id")  Integer id){
        log.info("Delete book. Book id to delete: {}", id);
        bookApplication.deleteBook(id);
    }

    @PutMapping ("/books/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    BookDTO updateBook(@PathVariable("id") Integer id, @RequestBody BookUpdateDTO book){
        log.info("Update book. BookUpdateDTO: {}", book);
        return bookApplication.updateBook (book);
    }
}
