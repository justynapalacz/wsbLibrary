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

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Book addBook(BookCreateDTO book) {
        return bookRepository.save(new Book(book.title(), book.author(), true));
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public Book updateBook(BookUpdateDTO bookUpdate) {
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
        return bookRepository.save(book);
    }
}
