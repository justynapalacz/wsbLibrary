package palaczjustyna.library.borrow.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.user.domain.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BorrowMapperTest {


    @InjectMocks
    private BorrowMapperImpl borrowMapper;

    @Test
    void shouldMapToDTO() {
        //given
        var id = 1;
        var dateOfBorrow = LocalDateTime.now();
        var dateOfReturn = LocalDateTime.now();
        var bookTitle = "Dziady";
        var bookAuthor = "Adam Mickiewicz";
        var firstNameUser = "Justyna";
        var lastNameUser = "Palacz";
        var emailUser = "justnapalacz@bilioteka.pl";

        Borrow borrow = new Borrow();
        borrow.setId(id);
        borrow.setDateOfBorrow(dateOfBorrow);
        borrow.setDateOfReturn(dateOfReturn);
        Book book = new Book();
        book.setTitle(bookTitle);
        book.setAuthor(bookAuthor);
        borrow.setBook(book);

        User user = new User();
        user.setFirstName(firstNameUser);
        user.setLastName(lastNameUser);
        user.setEmail(emailUser);
        borrow.setUser(user);

        //when
        var result = borrowMapper.mapToBarrowDTO(borrow);

        //then
        assertNotNull(result);
        assertEquals(dateOfBorrow, result.getDateOfBorrow());
        assertEquals(dateOfReturn, result.getDateOfReturn());
        assertEquals(bookTitle, result.getBookTitle());
        assertEquals(bookAuthor, result.getBookAuthor());
        assertEquals(firstNameUser, result.getFirstNameUser());
        assertEquals(lastNameUser, result.getLastNameUser());
        assertEquals(emailUser, result.getEmailUser());
    }
}