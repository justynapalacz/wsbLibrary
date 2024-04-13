package palaczjustyna.library.book.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import palaczjustyna.library.book.application.BookApplication;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.book.domain.BookCreateDTO;
import palaczjustyna.library.book.domain.BookDTO;
import palaczjustyna.library.book.domain.BookUpdateDTO;

import java.util.List;

@Tag(name = "Book Controller", description = "For Book management APIs")
@RestController
public class BookController {

    @Autowired
    private BookApplication bookApplication;

    @GetMapping("/getBooks")
    List<BookDTO> getAllBooks() {
        return bookApplication.getAllBooks();
    }

    @Operation(
    summary = "Retrieve a Books by title",
    description = "Get a Books object by specifying title. The response are books with  given title.")
    @GetMapping("/getBookByTitle")
    List<BookDTO> getBookByTitle(@RequestParam(value = "title")  String title){
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
