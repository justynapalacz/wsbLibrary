package palaczjustyna.library.book.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import palaczjustyna.library.book.infrastructure.BookRepository;

import java.util.List;

/**
 * The BookService class provides methods for managing book-related operations.
 * It interacts with the data access layer (repository) and performs business logic related to books.
 *
 * <p>This class is annotated with {@link Service} to indicate that it is a service component
 * and {@link Slf4j} for logging purposes.</p>
 *
 * @see BookRepository
 * @see BookMapper
 * @see BookDTO
 * @see BookCreateDTO
 * @see BookUpdateDTO
 * @see Book
 */
@Slf4j
@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    /**
     * Retrieves all books.
     *
     * @return a list of {@link BookDTO} objects representing all books.
     */
    public List<BookDTO> getAllBooks() {
        log.info("Search all books");
        List <Book> resultList = (List<Book>) bookRepository.findAll();
        return bookMapper.mapToBookListDTO(resultList);
    }

    /**
     * Retrieves books by their title.
     *
     * @param title the title of the book(s) to retrieve.
     * @return a list of {@link BookDTO} objects representing the books with the specified title.
     */
    public List<BookDTO> getBookByTitle(String title) {
        log.info("Search book by title. Title: {}", title);
        List <Book> resultList = bookRepository.findByTitle(title);
        return bookMapper.mapToBookListDTO(resultList);
    }

    /**
     * Adds a new book.
     *
     * @param book the {@link BookCreateDTO} object containing the details of the new book.
     * @return the {@link Book} object representing the added book.
     */
    public Book addBook(BookCreateDTO book) {
        log.info("Add book. BookCreateDTO: {}", book);
        return bookRepository.save(new Book(book.title(), book.author(), book.isbn(), true));
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete.
     */
    public void deleteBook(Integer id) {
        log.info("Delete book. Book id to delete: {}", id);
        bookRepository.deleteById(id);
    }

    /**
     * Updates an existing book.
     *
     * @param id        the ID of the book to update.
     * @param bookUpdate the {@link BookUpdateDTO} object containing the updated details of the book.
     * @return the {@link BookDTO} object representing the updated book.
     */
    public BookDTO updateBook(Integer id, BookUpdateDTO bookUpdate) {
        log.info("Update book. BookUpdateDTO: {}", bookUpdate);
        Book book = this.findById(id);
        if (bookUpdate.author() != null) {
            book.setAuthor(bookUpdate.author());
        }
        if (bookUpdate.title() != null) {
            book.setTitle(bookUpdate.title());
        }
        if (bookUpdate.isbn() != null) {
            book.setIsbn(bookUpdate.isbn());
        }
        if (bookUpdate.status() != null) {
            book.setStatus(bookUpdate.status());
        }
        return bookMapper.mapToBookDTO(bookRepository.save(book));
    }

    /**
     * Marks a book as returned.
     *
     * @param book the {@link Book} object to mark as returned.
     */
    public void returnBook(Book book) {
        log.info("Return book. Book: {}", book);
        book.setStatus(true);
        bookRepository.save(book);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book to retrieve.
     * @return the {@link Book} object with the specified ID.
     * @throws BookNotFoundException if the book with the specified ID is not found.
     */
    public Book findById(Integer id) {
        log.info("Search book by id: {}", id);
        return bookRepository.findById(id).orElseThrow( () -> new BookNotFoundException("The book with id = "+ id + " was not found."));
    }

}
