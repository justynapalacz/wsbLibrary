package palaczjustyna.library.book.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import palaczjustyna.library.book.domain.*;

import java.util.List;

/**
 * The BookApplication class provides methods for managing book-related operations.
 * It acts as an intermediary between the controller layer and the service layer.
 *
 * <p>This class is annotated with {@link Service} to indicate that it is a service component
 * and {@link Slf4j} for logging purposes.</p>
 *
 * <p>The class uses {@link BookService} to delegate the actual business logic related to books.</p>
 *
 * @see BookService
 * @see BookDTO
 * @see BookCreateDTO
 * @see BookUpdateDTO
 * @see Book
 */
@Slf4j
@Service
@AllArgsConstructor
public class BookApplication {

    private final BookService bookService;

    /**
     * Retrieves all books.
     *
     * @return a list of {@link BookDTO} objects representing all books.
     */
    public List<BookDTO> getAllBooks() {
        log.info("Search all books");
        return bookService.getAllBooks();
    }

    /**
     * Retrieves books by their title.
     *
     * @param title the title of the book(s) to retrieve.
     * @return a list of {@link BookDTO} objects representing the books with the specified title.
     */
    public List<BookDTO> getBookByTitle(String title) {
        log.info("Search book by title. Title: {}", title);
        return bookService.getBookByTitle(title);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book to retrieve.
     * @return the {@link Book} object with the specified ID.
     */
    public Book findById (Integer id){
        log.info("Search book by id: {}", id);
        return bookService.findById(id);
    }

    /**
     * Adds a new book.
     *
     * @param book the {@link BookCreateDTO} object containing the details of the new book.
     * @return the {@link Book} object representing the added book.
     */
    public Book addBook(BookCreateDTO book) {
        log.info("Add book. BookCreateDTO: {}", book);
        return bookService.addBook(book);
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete.
     */
    public void deleteBook(Integer id) {
        log.info("Delete book. Book id to delete: {}", id);
        bookService.deleteBook(id);
    }

    /**
     * Updates an existing book.
     *
     * @param id   the ID of the book to update.
     * @param book the {@link BookUpdateDTO} object containing the updated details of the book.
     * @return the {@link BookDTO} object representing the updated book.
     */
    public BookDTO updateBook(Integer id, BookUpdateDTO book) {
        log.info("Update book. BookUpdateDTO: {}", book);
        return bookService.updateBook(id, book);
    }

    /**
     * Marks a book as returned.
     *
     * @param book the {@link Book} object to mark as returned.
     */
    public void returnBook (Book book){
        log.info("Return book. Book: {}", book);
        bookService.returnBook(book);
    }
}
