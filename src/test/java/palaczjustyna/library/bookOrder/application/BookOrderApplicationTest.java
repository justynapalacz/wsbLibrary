package palaczjustyna.library.bookOrder.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.bookOrder.domain.BookOrderService;
import palaczjustyna.library.bookOrder.domain.BookToOrderDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookOrderApplicationTest {

    @Mock
    private BookOrderService bookOrderService;

    @InjectMocks
    private BookOrderApplication bookOrderApplication;

    @Test
    void shouldOrderBook() {
        //given
        String bookIsbn = "9788378876274";
        Integer quantity = 10;
        var message = "Successfully created order";
        BookToOrderDTO book = new BookToOrderDTO(bookIsbn, quantity);
        when(bookOrderService.createBookOrder(any())).thenReturn(message);

        //when
        var result = bookOrderApplication.createBookOrder(List.of(book));

        //then
        assertNotNull(result);
        assertEquals(message, result);
    }
}
