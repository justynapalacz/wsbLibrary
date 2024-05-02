package palaczjustyna.library.bookOrder.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import palaczjustyna.library.book.domain.BookDTO;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
@Service
@Slf4j
public class BookOrderService {

    @Value("${library.clientId.warehouse}")
    private Integer clientId;

    @Autowired
    private WebClient webClient;

    public String createBookOrder(BookToOrderDTO bookToOrderDTO) {
        Integer bookIdInBookWarehouse = findBookIdInBookWarehouse(bookToOrderDTO.bookTitle());
        Integer bookSummaryId = createBookSummary();
        Integer bookOrderId = addBookOrderToSummary(bookToOrderDTO.quantity(), bookSummaryId, bookIdInBookWarehouse);

        log.info("Book order added to summary. Book order id =" + bookOrderId);

        return "New book summary crated. Book summary id: " + bookSummaryId;
    }
    private Integer findBookIdInBookWarehouse(String bookTitle) {
        BookDTO bookDTO = webClient
                .method(HttpMethod.GET)
                .uri(builder -> builder.path("/getBookByTitle").queryParam("bookTitle", bookTitle).build())
                .header("Accept", "application/json, text/plain, */*")
                .retrieve()
                .bodyToMono(BookDTO.class)
                .block();

        return bookDTO.getId();
    }

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

        return result.id();
    }

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

        return result.id();
    }

}
