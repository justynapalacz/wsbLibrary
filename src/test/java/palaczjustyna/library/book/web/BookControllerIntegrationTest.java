package palaczjustyna.library.book.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import palaczjustyna.library.AbstractIntegrationTest;
import palaczjustyna.library.book.domain.BookCreateDTO;
import palaczjustyna.library.book.domain.BookDTO;
import palaczjustyna.library.book.domain.BookUpdateDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void testShouldReturnAllBooks() {
        //given
        //when
        var result = getAllBooks();

        //then
        assertNotNull(result);
        assertThat(result).hasSize(6);
        assertEquals("Pan Tadeusz", result[0].getTitle());
        assertEquals("9788307033419", result[0].getIsbn());
    }

    @Test
    public void testShouldReturnBookByTitle() {
        //given
        String title = "Dziady";

        //when
        var result = findBookByTitle(title, port);

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals("Dziady", result[0].getTitle());
        assertEquals("9788378876274", result[0].getIsbn());
    }

    @Test
    public void testShouldAddBook() {
        //given
        String title = "Konrad Wallenrod";
        String author = "Adam Mickiewicz";
        String isbn = "1234567891234";
        BookCreateDTO bookDTO = new BookCreateDTO(title, author, isbn);
        int bookSizeBeforeAdd = getAllBooks().length;

        //when
        var result = WebClient.create("http://localhost:" + port)
                .method(HttpMethod.POST)
                .uri("books")
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .body(BodyInserters.fromValue(bookDTO))
                .retrieve()
                .bodyToMono(BookDTO.class)
                .block();

        //then
        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(author, result.getAuthor());
        assertEquals(isbn, result.getIsbn());
        assertEquals(bookSizeBeforeAdd + 1, getAllBooks().length);
    }

    @Test
    public void testShouldDeleteBook(){
        //given
        Integer id = findBookByTitle("Afryka Kazika", port)[0].getId();
        int bookSizeBeforeDelete  =  getAllBooks().length;

        //when
        WebClient.create("http://localhost:" + port)
                .method(HttpMethod.DELETE)
                .uri(builder -> builder.path("/books/{id}").build(id))
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(void.class)
                .block();

        //then
        assertEquals(bookSizeBeforeDelete-1, getAllBooks().length);
    }

    @Test
    public void testShouldUpdateBook(){
        //given
        String title = "Dziady";
        Integer id = findBookByTitle(title, port)[0].getId();
        String newTitle = "Dziady2";

        BookUpdateDTO bookDTO = new BookUpdateDTO(id, newTitle, null, null, null);

        //when
        var result = WebClient.create("http://localhost:" + port)
                .method(HttpMethod.PUT)
                .uri(builder -> builder.path("/books/{id}").build(id))
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .body(BodyInserters.fromValue(bookDTO))
                .retrieve()
                .bodyToMono(BookDTO.class)
                .block();

        //then
        assertNotNull(result);
        assertEquals(newTitle, result.getTitle());
    }

    private BookDTO[] getAllBooks() {
        return WebClient.create("http://localhost:" + port)
                .method(HttpMethod.GET)
                .uri("books")
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(BookDTO[].class)
                .block();
    }

    public static BookDTO[] findBookByTitle(String title, int port) {
        return WebClient.create("http://localhost:" + port)
                .method(HttpMethod.GET)
                .uri(builder -> builder.path("/booksByTitle").queryParam("title", title).build())
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(BookDTO[].class)
                .block();
    }
}