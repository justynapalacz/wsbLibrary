package palaczjustyna.library.book.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.book.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookApplicationTest {
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookApplication bookApplication;

    @Test
    public void testShouldReturnAllBooks() {
        //given
        Integer id=1;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(id);
        when(bookService.getAllBooks()).thenReturn(List.of(bookDTO));

        //when
        var result = bookApplication.getAllBooks();

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(id, result.get(0).getId());
    }

    @Test
    void testShouldReturnBookByTitle() {
        //given
        String title = "Pan Tadeusz";
        BookDTO book = new BookDTO();
        book.setTitle(title);
        when(bookService.getBookByTitle(title)).thenReturn(List.of(book));

        //when
        var result = bookApplication.getBookByTitle(title);

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(title, result.get(0).getTitle());
    }

    @Test
    void testShouldAddBook() {
        //given
        Integer id = 1;
        String title = "Pan Tadeusz";
        String author = "Adam Mickiewicz";
        BookCreateDTO bookDTO = new BookCreateDTO(title, author);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        when(bookService.addBook(bookDTO)).thenReturn(book);

        //when
        var result = bookApplication.addBook(bookDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(author, result.getAuthor());
    }
    @Test
    public void testShouldDeleteBook(){
        //given
        Integer id = 1;

        //when
        bookApplication.deleteBook(id);

        //then
        verify(bookService, times(1)).deleteBook(id);
    }

    @Test
    public void testShouldUpdateBook() {
        //given
        Integer id = 1;
        String title = "Pan Tadeusz2";
        String author = "Adam Mickiewicz2";
        Boolean status = true;
        BookUpdateDTO bookDTO = new BookUpdateDTO(id, title, author, status);
        BookDTO book = new BookDTO();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        when(bookService.updateBook(bookDTO)).thenReturn(book);

        //when
        var result = bookApplication.updateBook(bookDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(author, result.getAuthor());
    }
}