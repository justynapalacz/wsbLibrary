package palaczjustyna.library.book.domain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.book.infrastructure.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

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
        BookCreateDTO bookDTO = new BookCreateDTO(title, author);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        when(bookRepository.save(any())).thenReturn(book);

        //when
        var result = bookService.addBook(bookDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(author, result.getAuthor());
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
        Boolean status = true;
        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO(id, title, author, status);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setStatus(status);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(id);
        bookDTO.setTitle(title);
        bookDTO.setAuthor(author);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);
        when(bookMapper.mapToBookDTO(any())).thenReturn(bookDTO);


        //when
        var result = bookService.updateBook(bookUpdateDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(author, result.getAuthor());
    }

    @Test
    @Disabled
    public void testShouldThrowExceptionDuringUpdateBook() {
        //given
        Integer id = 1;
        String title = "Proces2";
        String author = "Franz Kafka2";
        Boolean status = true;
        BookUpdateDTO bookDTO = new BookUpdateDTO(id, title, author, status);

        //when
        var exception = assertThrows(IllegalArgumentException.class, () -> bookService.updateBook(bookDTO));

        //then
        assertEquals("The book with id = " + id + " was not found", exception.getMessage());
    }

}