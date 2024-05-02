package palaczjustyna.library.bookOrder.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import palaczjustyna.library.book.domain.BookDTO;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookOrderServiceTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private  BookOrderService bookOrderService;

    @Test
    void shouldAddBookOrder() {
        //given
        var bookTitle = "Dziady";
        var quantity = 10;
        var bookOrderDto = new BookToOrderDTO(bookTitle, quantity);
        var bookDTO = new BookDTO();
        var summaryOrderDTO = new SummaryOrderDTO(10, LocalDateTime.now(), "NEW", "CARD", 1 );
        var summaryBookDTO = new SummaryBookDTO(null, 10, 2, 10);

        mockGetRequest(bookDTO);
        mockPostRequest(summaryOrderDTO, summaryBookDTO);

        //when
        var result = bookOrderService.createBookOrder(bookOrderDto);

        //then
        assertNotNull(result);
        assertEquals("New book summary crated. Book summary id: 10", result);
    }

    private void mockGetRequest(BookDTO bookDTO) {
        WebClient.RequestBodyUriSpec uriSpec =  mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodyUriSpec headerSpec =  mock(WebClient.RequestBodyUriSpec.class);
        WebClient.ResponseSpec response = mock(WebClient.ResponseSpec.class);

        when(webClient.method(HttpMethod.GET)).thenReturn(uriSpec);
        when(uriSpec.uri((Function<UriBuilder, URI>) any())).thenReturn(headerSpec);
        when(headerSpec.header(any(),any())).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(response);
        when(response.bodyToMono(BookDTO.class))
                .thenReturn(Mono.just(bookDTO));
    }

    private void mockPostRequest(SummaryOrderDTO summaryOrderDTO, SummaryBookDTO summaryBookDTO) {
        WebClient.RequestBodyUriSpec uriSpec =  mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodyUriSpec createSummaryBodySpec =  mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestHeadersSpec createSummaryHeaderSpec =  mock(WebClient.RequestBodyUriSpec.class);
        WebClient.ResponseSpec createSummaryResponse = mock(WebClient.ResponseSpec.class);

        when(webClient.method(HttpMethod.POST)).thenReturn(uriSpec);
        when(uriSpec.uri("/addSummary")).thenReturn(createSummaryBodySpec);
        when(createSummaryHeaderSpec.header(any(),any())).thenReturn(createSummaryHeaderSpec);
        when(createSummaryBodySpec.body(any())).thenReturn(createSummaryHeaderSpec);
        when(createSummaryHeaderSpec.retrieve()).thenReturn(createSummaryResponse);
        when(createSummaryResponse.bodyToMono(SummaryOrderDTO.class))
                .thenReturn(Mono.just(summaryOrderDTO));

        WebClient.RequestBodyUriSpec addBookBodySpec =  mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestHeadersSpec addBookHeaderSpec =  mock(WebClient.RequestBodyUriSpec.class);
        WebClient.ResponseSpec addBookResponse = mock(WebClient.ResponseSpec.class);

        when(uriSpec.uri("/addBookOrder")).thenReturn(addBookBodySpec);
        when(addBookHeaderSpec.header(any(),any())).thenReturn(addBookHeaderSpec);
        when(addBookBodySpec.body(any())).thenReturn(addBookHeaderSpec);
        when(addBookHeaderSpec.retrieve()).thenReturn(addBookResponse);
        when(addBookResponse.bodyToMono(SummaryBookDTO.class))
                .thenReturn(Mono.just(summaryBookDTO));
    }
}
