package palaczjustyna.library.bookOrder.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import palaczjustyna.library.book.domain.BookDTO;

import java.time.LocalDateTime;

@Service
@Slf4j
public class BookOrderService {

    @Value("${warehouse.url}")
    private String warehouseURL;

    @Value("${library.clientId.warehouse}")
    private Integer clientId;

    public String createBookOrder(String bookTitle, Integer quantity) {
        Integer bookIdInBookWarehouse = findBookIdInBookWarehouse(bookTitle);
        Integer bookSummaryId = createBookSummary();
        Integer bookOrderId = addBookOrderToSummary(quantity, bookSummaryId, bookIdInBookWarehouse);

        log.info("Book order added to summary. Book order id =" + bookOrderId);

        return "New book summary crated. Book summary id: " + bookSummaryId;
    }
    private Integer findBookIdInBookWarehouse(String bookTitle) {
        BookDTO bookDTO = WebClient.create(this.warehouseURL)
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

        SummaryOrderDTO result = WebClient.create(this.warehouseURL)
                .method(HttpMethod.POST)
                .uri(builder -> builder.path("/addSummary").build())
                .header("Accept", "application/json, text/plain, */*")
                .body(BodyInserters.fromValue(summaryOrderDTO))
                .retrieve()
                .bodyToMono(SummaryOrderDTO.class)
                .block();

        return result.id();
    }

    private Integer addBookOrderToSummary(Integer quantity, Integer bookSummaryId, Integer bookIdInBookWarehouse) {
        SummaryBookDTO summaryBookDTO = new SummaryBookDTO(null, bookSummaryId, bookIdInBookWarehouse, quantity);

        SummaryBookDTO result = WebClient.create(this.warehouseURL)
                .method(HttpMethod.POST)
                .uri(builder -> builder.path("/addBookOrder").build())
                .header("Accept", "application/json, text/plain, */*")
                .body(BodyInserters.fromValue(summaryBookDTO))
                .retrieve()
                .bodyToMono(SummaryBookDTO.class)
                .block();

        return result.id();
    }

}
