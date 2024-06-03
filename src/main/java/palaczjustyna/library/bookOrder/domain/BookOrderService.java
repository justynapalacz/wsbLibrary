package palaczjustyna.library.bookOrder.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import palaczjustyna.library.book.domain.BookDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * The BookOrderService class provides methods for managing book order-related operations.
 * It interacts with external services to process book orders.
 *
 * <p>This class is annotated with {@link Service} to indicate that it is a service component
 * and {@link Slf4j} for logging purposes.</p>
 *
 * @see WebClient
 * @see BookToOrderDTO
 * @see BookDTO
 * @see SummaryOrderDTO
 * @see SummaryBookDTO
 */
@Service
@Slf4j
public class BookOrderService {

    @Value("${library.clientId.warehouse}")
    private Integer clientId;

    @Autowired
    @Qualifier("webClientForBookOrder")
    private WebClient webClient;

    /**
     * Creates a new book order.
     *
     * @param bookToOrderDTOs a list of {@link BookToOrderDTO} objects containing the details of the books to order.
     * @return a string message indicating the result of the book order creation process.
     */
    public String createBookOrder(List<BookToOrderDTO> bookToOrderDTOs) {
        log.info("Create book order. Book order list {}", bookToOrderDTOs);
        Integer bookSummaryId = createBookSummary();

        bookToOrderDTOs.forEach(bookToOrderDTO -> {
                    Integer bookIdInBookWarehouse = findBookIdInBookWarehouse(bookToOrderDTO.bookIsbn());
                    Integer bookOrderId = addBookOrderToSummary(bookToOrderDTO.quantity(), bookSummaryId, bookIdInBookWarehouse);
                    log.info("Book order added to summary. Book order id =" + bookOrderId);
                }
        );

        return "New book summary crated. Book summary id: " + bookSummaryId;
    }

    /**
     * Retrieves the ID of the book in the book warehouse by its ISBN.
     *
     * @param bookIsbn the ISBN of the book.
     * @return the ID of the book in the book warehouse.
     */
    private Integer findBookIdInBookWarehouse(String bookIsbn) {
        BookDTO bookDTO = webClient
                .method(HttpMethod.GET)
                .uri(builder -> builder.path("/getBookByIsbn").queryParam("isbn", bookIsbn).build())
                .header("Accept", "application/json, text/plain, */*")
                .retrieve()
                .bodyToMono(BookDTO.class)
                .block();

        return Objects.requireNonNull(bookDTO).getId();
    }

    /**
     * Creates a new summary order.
     *
     * @return the ID of the newly created summary order.
     */
    private Integer createBookSummary() {
        SummaryOrderDTO summaryOrderDTO = new SummaryOrderDTO(null, LocalDateTime.now(), "NEW", "Bank transfer", clientId);
        SummaryOrderDTO result = webClient
                .method(HttpMethod.POST)
                .uri("/addSummary")
                .body(BodyInserters.fromValue(summaryOrderDTO))
                .header("Accept", "application/json, text/plain, */*")
                .retrieve()
                .bodyToMono(SummaryOrderDTO.class)
                .block();

        return Objects.requireNonNull(result).id();
    }

    /**
     * Adds a book order to the summary order.
     *
     * @param quantity         the quantity of the book to order.
     * @param bookSummaryId    the ID of the book summary.
     * @param bookIdInBookWarehouse the ID of the book in the book warehouse.
     * @return the ID of the newly created book order.
     */
    private Integer addBookOrderToSummary(Integer quantity, Integer bookSummaryId, Integer bookIdInBookWarehouse) {
        SummaryBookDTO summaryBookDTO = new SummaryBookDTO(null, bookSummaryId, bookIdInBookWarehouse, quantity);
        SummaryBookDTO result = webClient
                .method(HttpMethod.POST)
                .uri("/addBookOrder")
                .body(BodyInserters.fromValue(summaryBookDTO))
                .header("Accept", "application/json, text/plain, */*")
                .retrieve()
                .bodyToMono(SummaryBookDTO.class)
                .block();

        return Objects.requireNonNull(result).id();
    }

}
