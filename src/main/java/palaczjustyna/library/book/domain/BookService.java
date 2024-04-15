package palaczjustyna.library.book.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.book.infrastructure.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> getAllBooks() {
        List <Book> resultList = (List<Book>) bookRepository.findAll();
        return bookMapper.mapToBookListDTO(resultList);
    }

    public List<BookDTO> getBookByTitle(String title) {
        List <Book> resultList = bookRepository.findByTitle(title);
        return bookMapper.mapToBookListDTO(resultList);
    }

    public Book addBook(BookCreateDTO book) {
        return bookRepository.save(new Book(book.title(), book.author(), true));
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public BookDTO updateBook(BookUpdateDTO bookUpdate) {
        Optional<Book> bookOpt = bookRepository.findById(bookUpdate.id());
        if (bookOpt.isEmpty()) {
            throw new IllegalArgumentException("The book with id = " +  bookUpdate.id() + " was not found" );
        }
        Book book = bookOpt.get();
        if (bookUpdate.author() != null) {
            book.setAuthor(bookUpdate.author());
        }
        if (bookUpdate.title() != null) {
            book.setTitle(bookUpdate.title());
        }
        if (bookUpdate.status() != null) {
            book.setStatus(bookUpdate.status());
        }
        return bookMapper.mapToBookDTO(bookRepository.save(book));
    }

    public Book findById(Integer id) {
        return bookRepository.findById(id).orElseThrow();
    }
}
