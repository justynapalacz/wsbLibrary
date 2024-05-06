package palaczjustyna.library.borrow.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import palaczjustyna.library.AbstractIntegrationTest;
import palaczjustyna.library.book.web.BookControllerIntegrationTest;
import palaczjustyna.library.borrow.domain.BorrowCreateDTO;
import palaczjustyna.library.borrow.domain.BorrowDTO;
import palaczjustyna.library.user.web.UserControllerIntegrationTest;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BorrowControllerIntegrationTest extends AbstractIntegrationTest {

    @Value("${penalty.rate}")
    private double penaltyRate;
    @Value("${allowed.number.of.days}")
    private int allowedDays;

    @Test
    void shouldReturnAllBorrows(){
        //given
        //when
        var result = getAllBorrows();

        //then
        assertNotNull(result);
        assertThat(result).hasSize(4);
    }

    @Test
    void shouldReturnBarrowById(){
        //given
        var barrowId = getAllBorrows()[0].getId();

        //when
        var result = getBorrowById(barrowId);

        //then
        assertNotNull(result);
        assertEquals(barrowId, result.getId());
    }

    @Test
    public void testShouldAddBorrow() {
        //given
        int borrowsBeforeAddingNewOne = getAllBorrows().length;
        int bookId = BookControllerIntegrationTest.findBookByTitle("Afryka Kazika", port)[0].getId();
        int userId = UserControllerIntegrationTest.findUserByLastName("Nowak", port)[0].id();

        BorrowCreateDTO borrowCreateDTO = new BorrowCreateDTO(bookId, userId);

        //when
        var result =  WebClient.create("http://localhost:" + port)
                .method(HttpMethod.POST)
                .uri("addBorrow")
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .body(BodyInserters.fromValue(borrowCreateDTO))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //then
        assertNotNull(result);
        assertEquals("Successfully created borrow", result);
        assertEquals(borrowsBeforeAddingNewOne + 1, getAllBorrows().length);
    }

    @Test
    void shouldReturnBook(){
        // given
        int borrowId = getAllBorrows()[1].getId();

        // when
        var result =  WebClient.create("http://localhost:" + port)
                .method(HttpMethod.PUT)
                .uri(builder -> builder.path("/returnBook").queryParam("id", borrowId).build())
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // then
        assertNotNull(result);
        assertEquals("Successfully return", result);
    }

    @Test
    void shouldChargePenalty(){
        // given
        BorrowDTO borrow = getAllBorrows()[1];
        int borrowId = borrow.getId();

        // when
        var result =  WebClient.create("http://localhost:" + port)
                .method(HttpMethod.GET)
                .uri(builder -> builder.path("/checkPenalty").queryParam("id", borrowId).build())
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // then
        assertNotNull(result);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDate = borrow.getDateOfBorrow();
        long diff = ChronoUnit.DAYS.between(firstDate, now);
        assertEquals("Cash penalty: "+ ((diff - allowedDays) * penaltyRate), result);
    }

    private BorrowDTO[] getAllBorrows() {
        return WebClient.create("http://localhost:" + port)
                .method(HttpMethod.GET)
                .uri("getBorrows")
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(BorrowDTO[].class)
                .block();
    }

    private BorrowDTO getBorrowById(int id) {
        return WebClient.create("http://localhost:" + port)
                .method(HttpMethod.GET)
                .uri(builder -> builder.path("/getBorrowsById").queryParam("id", id).build())
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(BorrowDTO.class)
                .block();
    }
}