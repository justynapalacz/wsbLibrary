package palaczjustyna.library.bookOrder.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import palaczjustyna.library.bookOrder.application.BookOrderApplication;
import palaczjustyna.library.bookOrder.domain.BookToOrderDTO;

import java.util.List;

/**
 * The BookOrderController class provides RESTful APIs for managing book orders in the library system.
 * It includes endpoints for creating new book orders.
 *
 * <p>This class is annotated with {@link RestController} to indicate that it is a web controller
 * and {@link Slf4j} for logging purposes.</p>
 *
 * <p>The controller uses {@link BookOrderApplication} to handle the business logic related to book orders.</p>
 *
 * @see BookOrderApplication
 * @see BookToOrderDTO
 */
@Slf4j
@RestController
@AllArgsConstructor
public class BookOrderController {

    private final BookOrderApplication bookOrderApplication;


    /**
     * Creates a new book order.
     *
     * <p>This method is accessible only to users with the 'ROLE_ADMIN' authority.</p>
     *
     * <p>The method is mapped to the HTTP POST request at the "/orderBooks" endpoint and returns
     * a confirmation string with an HTTP status of 201 (CREATED).</p>
     *
     * <p>Example usage:</p>
     * <pre>
     * {@code
     * // Example HTTP POST request
     * POST /orderBooks HTTP/1.1
     * Content-Type: application/json
     *
     * [
     *   {
     *     "isbn": "1234567890",
     *     "quantity": 10
     *   },
     *   {
     *     "isbn": "0987654321",
     *     "quantity": 5
     *   }
     * ]
     *
     * // Example response
     * HTTP/1.1 201 Created
     * Content-Type: text/plain
     *
     * "New book summary crated. Book summary id: 1"
     * }
     * </pre>
     *
     * @param bookOrderDTOs the list of {@link BookToOrderDTO} objects containing the details of the books to be ordered.
     * @return a confirmation string indicating the order was created successfully.
     */
    @PostMapping("/orderBooks")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String createBookOrder(@Valid @RequestBody List<BookToOrderDTO> bookOrderDTOs) {
        log.info("Create book order. Book order list {}", bookOrderDTOs);
        return bookOrderApplication.createBookOrder(bookOrderDTOs);
    }
}
