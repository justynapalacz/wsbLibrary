package palaczjustyna.library.book.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.book.application.BookApplication;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.book.domain.BookCreateDTO;
import palaczjustyna.library.book.domain.BookDTO;
import palaczjustyna.library.book.domain.BookUpdateDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookApplication bookApplication;

    @InjectMocks
    private BookController bookController;

    @Test
    public void testShouldReturnAllBooks() {
        //given
        Integer id = 1;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(id);
        when(bookApplication.getAllBooks()).thenReturn(List.of(bookDTO));

        //when
        var result = bookController.getAllBooks();

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(id, result.get(0).getId());
    }

    @Test
    public void testShouldReturnBookByTitle() {
        //given
        String title = "Dziady";
        BookDTO book = new BookDTO();
        book.setTitle(title);
        when(bookApplication.getBookByTitle(title)).thenReturn(List.of(book));

        //when
        var result = bookController.getBookByTitle(title);

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(title, result.get(0).getTitle());
    }

    @Test
    public void testShouldAddBook() {
        //given
        Integer id = 1;
        String title = "Dziady";
        String author = "Adam Mickiewicz";
        BookCreateDTO bookDTO = new BookCreateDTO(title, author);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        when(bookApplication.addBook(bookDTO)).thenReturn(book);

        //when
        var result = bookController.addBook(bookDTO);

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
        bookController.deleteBook(id);

        //then
        verify(bookApplication, times(1)).deleteBook(id);
    }

    @Test
    public void testShouldUpdateBook() {
        //given
        Integer id = 1;
        String title = "Dziady2";
        String author = "Adam Mickiewicz2";
        Boolean status = true;
        BookUpdateDTO bookDTO = new BookUpdateDTO(id, title, author, status);
        BookDTO book = new BookDTO();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        when(bookApplication.updateBook(bookDTO)).thenReturn(book);

        //when
        var result = bookController.updateBook(bookDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(author, result.getAuthor());
    }
}