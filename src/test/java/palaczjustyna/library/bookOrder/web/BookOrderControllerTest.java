package palaczjustyna.library.bookOrder.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.bookOrder.application.BookOrderApplication;
import palaczjustyna.library.bookOrder.domain.BookToOrderDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookOrderControllerTest {
    @Mock
    private BookOrderApplication bookOrderApplication;
    @InjectMocks
    private BookOrderController bookOrderController;

    @Test
    void shouldOrderBook() {
        //given
        String bookTitle = "Dziady";
        Integer quantity = 10;
        var message = "Successfully created order";
        BookToOrderDTO book = new BookToOrderDTO(bookTitle, quantity);
        when(bookOrderApplication.createBookOrder(book)).thenReturn(message);

        //when
        var result = bookOrderController.orderBooks(book);

        //then
        assertNotNull(result);
        assertEquals(message, result);
    }

}
