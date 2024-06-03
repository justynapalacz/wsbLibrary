package palaczjustyna.library.book.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.book.infrastructure.BookRepository;
import palaczjustyna.library.borrow.domain.Borrow;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testShouldReturnAllBooks() {
        //given
        Integer id = 1;
        Book book = new Book();
        book.setId(id);
        when(bookRepository.findAll()).thenReturn(List.of(book));

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(id);
        when(bookMapper.mapToBookListDTO(any())).thenReturn(List.of(bookDTO));

        //when
        var result = bookService.getAllBooks();

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(id, result.get(0).getId());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testShouldReturnBookByTitle() {
        //given
        String title = "Proces";
        Book book = new Book();
        book.setTitle(title);
        when(bookRepository.findByTitle(title)).thenReturn(List.of(book));

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(title);
        when(bookMapper.mapToBookListDTO(any())).thenReturn(List.of(bookDTO));

        //when
        var result = bookService.getBookByTitle(title);

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(title, result.get(0).getTitle());
    }

    @Test
    void testShouldAddBook() {
        //given
        Integer id = 1;
        String title = "Proces";
        String author = "Franz Kafka";
        String isbn = "1234567891234";
        BookCreateDTO bookDTO = new BookCreateDTO(title, author, isbn);
        Integer borrowId = 100;
        Borrow borrow = new Borrow();
        borrow.setId(borrowId);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setBorrowList(List.of(borrow));
        when(bookRepository.save(any())).thenReturn(book);

        //when
        var result = bookService.addBook(bookDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(author, result.getAuthor());
        assertEquals(isbn, result.getIsbn());
        assertEquals(borrowId, result.getBorrowList().get(0).getId());
    }

    @Test
    public void testShouldDeleteBook() {
        //given
        Integer id = 1;

        //when
        bookService.deleteBook(id);

        //then
        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    public void testShouldUpdateBook() {
        //given
        Integer id = 1;
        String title = "Proces2";
        String author = "Franz Kafka2";
        String isbn = "1234567891234";
        Boolean status = true;
        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO(title, author, isbn, status);

        Book book = new Book();
        book.setId(id);
        book.setTitle("oldTitle");
        book.setAuthor("oldAuthor");
        book.setIsbn("oldIsb");
        book.setStatus(!status);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(id);
        bookDTO.setTitle(title);
        bookDTO.setAuthor(author);
        bookDTO.setIsbn(isbn);
        ArgumentCaptor<Book> argument = ArgumentCaptor.forClass(Book.class);


        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.mapToBookDTO(book)).thenReturn(bookDTO);

        //when
        var result = bookService.updateBook(id, bookUpdateDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(author, result.getAuthor());
        assertEquals(isbn, result.getIsbn());

        verify(bookRepository).save(argument.capture());
        assertEquals(author, argument.getValue().getAuthor());
        assertEquals(isbn, argument.getValue().getIsbn());
        assertEquals(title, argument.getValue().getTitle());
        assertEquals(status, argument.getValue().getStatus());
    }

    @Test
    public void testShouldThrowExceptionDuringUpdateBook() {
        //given
        Integer id = 1;
        String title = "Proces2";
        String author = "Franz Kafka2";
        String isbn = "1234567891234";
        Boolean status = true;
        BookUpdateDTO bookDTO = new BookUpdateDTO(title, author,isbn, status);

        //when
        var exception = assertThrows(BookNotFoundException.class, () -> bookService.updateBook(id, bookDTO));

        //then
        assertEquals("The book with id = " + id + " was not found.", exception.getMessage());
    }

    @Test
    public void testShouldReturnBook() {
        //given
        Book book = new Book();

        //when
        bookService.returnBook(book);

        //then
        verify(bookRepository, times(1)).save(book);
    }


}