package palaczjustyna.library.book.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    @InjectMocks
    private BookMapperImpl bookMapper;

    @Test
    void shouldMapToBookDTO() {
        //given
        Integer id = 1;
        String title = "Proces";
        String author = "Franz Kafka";
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setStatus(true);

        //when
        var result = bookMapper.mapToBookDTO(book);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(author, result.getAuthor());
        assertEquals("Available", result.getStatus());
    }

    @Test
    void shouldMapToBookStatus() {
        //given
        Boolean bookStatus = false;

        //when
        var result = bookMapper.mapToBookStatus(bookStatus);

        //then
        assertNotNull(result);
        assertEquals("Borrowed", result);
    }
}