package palaczjustyna.library.borrow.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.borrow.domain.BorrowCreateDTO;
import palaczjustyna.library.borrow.domain.BorrowDTO;
import palaczjustyna.library.borrow.domain.BorrowService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowApplicationTest {

    @Mock
    private BorrowService borrowService;

    @InjectMocks
    private BorrowApplication borrowApplication;

    @Test
    void shouldReturnAllBarrow() {
        //given
        var id = 1;
        var dateOfBorrow = LocalDateTime.now();
        var dateOfReturn = LocalDateTime.now();
        var bookTitle = "Dziady";
        var bookAuthor = "Adam Mickiewicz";
        var firstNameUser = "Justyna";
        var lastNameUser = "Palacz";
        var emailUser = "justnapalacz@bilioteka.pl";

        BorrowDTO borrowDTO = new BorrowDTO();
        borrowDTO.setId(id);
        borrowDTO.setDateOfBorrow(dateOfBorrow);
        borrowDTO.setDateOfReturn(dateOfReturn);
        borrowDTO.setBookTitle(bookTitle);
        borrowDTO.setBookAuthor(bookAuthor);
        borrowDTO.setFirstNameUser(firstNameUser);
        borrowDTO.setLastNameUser(lastNameUser);
        borrowDTO.setEmailUser(emailUser);

        when(borrowService.getAllBorrows()).thenReturn(List.of(borrowDTO));

        //when
        var result = borrowApplication.getAllBorrows();
        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(id, result.get(0).getId());
        assertEquals(dateOfBorrow, result.get(0).getDateOfBorrow());
        assertEquals(dateOfReturn, result.get(0).getDateOfReturn());
        assertEquals(bookTitle, result.get(0).getBookTitle());
        assertEquals(bookAuthor, result.get(0).getBookAuthor());
        assertEquals(firstNameUser, result.get(0).getFirstNameUser());
        assertEquals(lastNameUser, result.get(0).getLastNameUser());
        assertEquals(emailUser, result.get(0).getEmailUser());
    }

    @Test
    void shouldReturnBarrowById() {
        //given
        var id = 1;
        var dateOfBorrow = LocalDateTime.now();
        var dateOfReturn = LocalDateTime.now();
        var bookTitle = "Dziady";
        var bookAuthor = "Adam Mickiewicz";
        var firstNameUser = "Justyna";
        var lastNameUser = "Palacz";
        var emailUser = "justnapalacz@bilioteka.pl";

        BorrowDTO borrowDTO = new BorrowDTO();
        borrowDTO.setId(id);
        borrowDTO.setDateOfBorrow(dateOfBorrow);
        borrowDTO.setDateOfReturn(dateOfReturn);
        borrowDTO.setBookTitle(bookTitle);
        borrowDTO.setBookAuthor(bookAuthor);
        borrowDTO.setFirstNameUser(firstNameUser);
        borrowDTO.setLastNameUser(lastNameUser);
        borrowDTO.setEmailUser(emailUser);

        when(borrowService.getBorrowsDTOById(id)).thenReturn(borrowDTO);

        //when
        var result = borrowApplication.getBorrowsById(id);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(dateOfBorrow, result.getDateOfBorrow());
        assertEquals(dateOfReturn, result.getDateOfReturn());
        assertEquals(bookTitle, result.getBookTitle());
        assertEquals(bookAuthor, result.getBookAuthor());
        assertEquals(firstNameUser, result.getFirstNameUser());
        assertEquals(lastNameUser, result.getLastNameUser());
        assertEquals(emailUser, result.getEmailUser());
    }

    @Test
    void shouldAddBarrow() {
        //given
        var bookId = 10;
        var userId = 1;
        var message = "Successfully created borrow";
        BorrowCreateDTO borrowCreateDTO = new BorrowCreateDTO(bookId, userId);
        when(borrowService.addBorrow(borrowCreateDTO)).thenReturn(message);

        //when
        var result = borrowApplication.addBorrow(borrowCreateDTO);

        //then
        assertNotNull(result);
        assertEquals(message, result);
    }

    @Test
    void shouldUpdateBorrowAndReturnBook() {
        // given
        var barrowId = 10;
        var message = "Successfully return";

        when(borrowService.updateBorrowAndReturnBook(barrowId)).thenReturn(message);

        //when
        var result = borrowApplication.updateBorrowAndReturnBook(barrowId);

        //then
        assertNotNull(result);
        assertEquals(message, result);
    }


    @Test
    void shouldChargePenalty() {
        // given
        var barrowId = 10;
        var message = "The book is returned on time";

        when(borrowService.chargePenalty(barrowId)).thenReturn(message);

        //when
        var result = borrowApplication.chargePenalty(barrowId);

        //then
        assertNotNull(result);
        assertEquals(message, result);
    }


    @Test
    void shouldSendEmail() {
        //given
        var id = 1;
        var dateOfBorrow = LocalDateTime.now();
        var dateOfReturn = LocalDateTime.now();
        var bookTitle = "Dziady";
        var bookAuthor = "Adam Mickiewicz";
        var firstNameUser = "Justyna";
        var lastNameUser = "Palacz";
        var emailUser = "justnapalacz@bilioteka.pl";

        BorrowDTO borrowDTO = new BorrowDTO();
        borrowDTO.setId(id);
        borrowDTO.setDateOfBorrow(dateOfBorrow);
        borrowDTO.setDateOfReturn(dateOfReturn);
        borrowDTO.setBookTitle(bookTitle);
        borrowDTO.setBookAuthor(bookAuthor);
        borrowDTO.setFirstNameUser(firstNameUser);
        borrowDTO.setLastNameUser(lastNameUser);
        borrowDTO.setEmailUser(emailUser);


        when(borrowService.sendEmail()).thenReturn(List.of(borrowDTO));

        //when
        var result = borrowApplication.sendEmail();

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(id, result.get(0).getId());
        assertEquals(dateOfBorrow, result.get(0).getDateOfBorrow());
        assertEquals(dateOfReturn, result.get(0).getDateOfReturn());
        assertEquals(bookTitle, result.get(0).getBookTitle());
        assertEquals(bookAuthor, result.get(0).getBookAuthor());
        assertEquals(firstNameUser, result.get(0).getFirstNameUser());
        assertEquals(lastNameUser, result.get(0).getLastNameUser());
        assertEquals(emailUser, result.get(0).getEmailUser());
    }

}