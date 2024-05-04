package palaczjustyna.library.book.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.book.infrastructure.BookRepository;

import java.util.List;

@Slf4j
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> getAllBooks() {
        log.info("Search all books");
        List <Book> resultList = (List<Book>) bookRepository.findAll();
        return bookMapper.mapToBookListDTO(resultList);
    }

    public List<BookDTO> getBookByTitle(String title) {
        log.info("Search book by title. Title: {}", title);
        List <Book> resultList = bookRepository.findByTitle(title);
        return bookMapper.mapToBookListDTO(resultList);
    }

    public Book addBook(BookCreateDTO book) {
        log.info("Add book. BookCreateDTO: {}", book);
        return bookRepository.save(new Book(book.title(), book.author(), book.isbn(), true));
    }

    public void deleteBook(Integer id) {
        log.info("Delete book. Book id to delete: {}", id);
        bookRepository.deleteById(id);
    }

    public BookDTO updateBook(BookUpdateDTO bookUpdate) {
        log.info("Update book. BookUpdateDTO: {}", bookUpdate);
        Book book = this.findById(bookUpdate.id());
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

    public void returnBook(Book book) {
        log.info("Return book. Book: {}", book);
        book.setStatus(true);
        bookRepository.save(book);
    }

    public Book findById(Integer id) {
        log.info("Search book by id: {}", id);
        return bookRepository.findById(id).orElseThrow( () -> new BookNotFoundException("The book with id = "+ id + " was not found."));
    }

}
