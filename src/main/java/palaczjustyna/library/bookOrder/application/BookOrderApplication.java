package palaczjustyna.library.bookOrder.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import palaczjustyna.library.bookOrder.domain.BookOrderService;
import palaczjustyna.library.bookOrder.domain.BookToOrderDTO;

import java.util.List;

/**
 * The BookOrderApplication class provides methods for managing book order-related operations.
 * It acts as an intermediary between the controller layer and the service layer.
 *
 * <p>This class is annotated with {@link Service} to indicate that it is a service component
 * and {@link Slf4j} for logging purposes.</p>
 *
 * @see BookOrderService
 * @see BookToOrderDTO
 */
@Slf4j
@Service
@AllArgsConstructor
public class BookOrderApplication {

    private final BookOrderService bookOrderService;

    /**
     * Creates a new book order.
     *
     * @param bookToOrderDTOs a list of {@link BookToOrderDTO} objects containing the details of the books to order.
     * @return a string message indicating the result of the book order creation process.
     */
    public String createBookOrder(List<BookToOrderDTO> bookToOrderDTOs){
        log.info("Create book order. Book order list {}", bookToOrderDTOs);
       return bookOrderService.createBookOrder(bookToOrderDTOs);
    }
}
