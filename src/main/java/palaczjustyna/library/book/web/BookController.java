package palaczjustyna.library.book.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import palaczjustyna.library.book.application.BookApplication;
import palaczjustyna.library.book.domain.*;

import java.util.List;

/**
 * The BookController class provides RESTful APIs for managing books in the library system.
 * It supports operations such as retrieving, creating, updating, and deleting books.
 * This controller handles requests related to books and delegates the business logic to the BookApplication.
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Retrieve a list of all books
 * List<Book> books = bookController.getAllBooks();
 *
 * // Get a book by title
 * Book book = bookController.getBookByTitle("Dziady");
 *
 *
 * // Delete a book
 * bookController.deleteBook(1L);
 * }
 * </pre>
 *
 * <p>This class is a Spring {@link RestController} and uses {@link Autowired} to inject the {@link BookApplication}.</p>
 *
 * @see Book
 * @see BookApplication
 * @see RequestMapping
 * @see GetMapping
 * @see PostMapping
 * @see PutMapping
 * @see DeleteMapping
 */
@Slf4j
@RestController
public class BookController {

    @Autowired
    private BookApplication bookApplication;

    /**
     * Retrieves a list of all books.
     *
     * <p>This method is accessible only to users with the 'ROLE_READER' authority.</p>
     *
     * <p>The method is mapped to the HTTP GET request at the "/books" endpoint and returns
     * a list of all books with an HTTP status of 200 (OK).</p>
     *
     * <p>Example usage:</p>
     * <pre>
     * {@code
     * // Example HTTP GET request
     * GET /books HTTP/1.1
     * Host: example.com
     *
     * // Example response
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * [
     *   {
     *     "id": 1,
     *     "title": "Pan Tadeusz",
     *     "author": "Adam Mickiewicz",
     *     "isbn": "9788307033419",
     *     "status": active
     *   },
     *   {
     *     "id": 2,
     *     "title": "Dziady",
     *     "author": "Adam Mickiewicz",
     *     "isbn": "9788378876274",
     *     "status": active
     *   }
     * ]
     * }
     * </pre>
     *
     * @return a list of {@link BookDTO} objects representing all books in the library.
     */
    @GetMapping("/books")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    @ResponseStatus(HttpStatus.OK)
    List<BookDTO> getAllBooks() {
        log.info("Search all books");
        return bookApplication.getAllBooks();
    }

    /**
     * Retrieves a list of books that match the specified title.
     *
     * <p>This method is accessible only to users with the 'ROLE_READER' authority.</p>
     *
     * <p>The method is mapped to the HTTP GET request at the "/booksByTitle" endpoint and returns
     * a list of books that have titles matching the given parameter, with an HTTP status of 200 (OK).</p>
     *
     * <p>Example usage:</p>
     * <pre>
     * {@code
     * // Example HTTP GET request
     * GET /booksByTitle?title=Dziady HTTP/1.1
     * Host: example.com
     *
     * // Example response
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * [
     *   {
     *     "id": 2,
     *     "title": "Dziady",
     *     "author": "Adam Mickiewicz",
     *     "isbn": "9788378876274",
     *     "status": active
     *   }
     * ]
     * }
     * </pre>
     *
     * @param title the title of the books to search for.
     * @return a list of {@link BookDTO} objects that match the specified title.
     */
    @GetMapping("/booksByTitle")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    @ResponseStatus(HttpStatus.OK)
    List<BookDTO> getBookByTitle(@RequestParam(value = "title")  String title){
        log.info("Search book by title. Title: {}" ,title);
        return bookApplication.getBookByTitle(title);
    }

    /**
     * Adds a new book to the library system.
     *
     * <p>This method is accessible only to users with the 'ROLE_LIBRARIAN' authority.</p>
     *
     * <p>The method is mapped to the HTTP POST request at the "/books" endpoint and returns
     * the created book with an HTTP status of 201 (CREATED).</p>
     *
     * <p>Example usage:</p>
     * <pre>
     * {@code
     * // Example HTTP POST request
     * POST /books HTTP/1.1
     * Host: example.com
     * Content-Type: application/json
     *
     * {
     *   "title": "Dziady",
     *   "author": "Adam Mickiewicz",
     *   "isbn": "9788378876274"
     * }
     *
     * // Example response
     * HTTP/1.1 201 Created
     * Content-Type: application/json
     *
     *   {
     *     "id": 1,
     *     "title": "Dziady",
     *     "author": "Adam Mickiewicz",
     *     "isbn": "9788378876274",
     *     "status": active
     *   }
     * }
     * </pre>
     *
     * @param book the {@link BookCreateDTO} object containing the details of the book to be added.
     * @return the created {@link Book} object.
     */
    @PostMapping("/books")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    Book addBook(@Valid @RequestBody BookCreateDTO book){
        log.info("Add book. BookCreateDTO: {}", book);
        return bookApplication.addBook(book);
    }

    /**
     * Deletes a book from the library system by its ID.
     *
     * <p>This method is accessible only to users with the 'ROLE_ADMIN' authority.</p>
     *
     * <p>The method is mapped to the HTTP DELETE request at the "/books/{id}" endpoint
     * and returns an HTTP status of 204 (NO CONTENT) upon successful deletion.</p>
     *
     * <p>Example usage:</p>
     * <pre>
     * {@code
     * // Example HTTP DELETE request
     * DELETE /books/1 HTTP/1.1
     * Host: example.com
     *
     * // Example response
     * HTTP/1.1 204 No Content
     * }
     * </pre>
     *
     * @param id the ID of the book to delete.
     */
    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBook (@PathVariable(value = "id")  Integer id){
        log.info("Delete book. Book id to delete: {}", id);
        bookApplication.deleteBook(id);
    }

    /**
     * Updates an existing book in the library system.
     *
     * <p>This method is accessible only to users with the 'ROLE_LIBRARIAN' authority.</p>
     *
     * <p>The method is mapped to the HTTP PUT request at the "/books/{id}" endpoint and returns
     * the updated book as a {@link BookDTO} object with an HTTP status of 200 (OK).</p>
     *
     * <p>Example usage:</p>
     * <pre>
     * {@code
     * // Example HTTP PUT request
     * PUT /books/1 HTTP/1.1
     * Host: example.com
     * Content-Type: application/json
     *
     * {
     *   "id": "1"
     *   "title": "Dziady2"
     * }
     *
     * // Example response
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * {
     *   "id": 1,
     *   "title": "Dziady2",
     *   "author": "Adam Mickiewicz",
     *   "isbn": "9788378876274",
     *   "status": active
     * }
     * }
     * </pre>
     *
     * @param id the ID of the book to update.
     * @param book the {@link BookUpdateDTO} object containing the updated details of the book.
     * @return the updated {@link BookDTO} object.
     */
    @PutMapping ("/books/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    BookDTO updateBook(@PathVariable("id") Integer id, @RequestBody BookUpdateDTO book){
        log.info("Update book. BookUpdateDTO: {}", book);
        return bookApplication.updateBook (id, book);
    }

    /**
     * Handles the {@link BookNotFoundException} exception.
     * Returns a response entity with HTTP status 404 (NOT_FOUND) and the exception message in the body.
     *
     * @param ex the {@link BookNotFoundException} exception to handle.
     * @return a {@link ResponseEntity} with HTTP status 404 and the exception message.
     */
    @ExceptionHandler({BookNotFoundException.class})
    public ResponseEntity<String> handleException(BookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
