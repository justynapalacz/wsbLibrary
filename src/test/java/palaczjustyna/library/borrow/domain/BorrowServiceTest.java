package palaczjustyna.library.borrow.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.book.application.BookApplication;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.borrow.infrastructure.BorrowRepository;
import palaczjustyna.library.email.application.EmailApplication;
import palaczjustyna.library.user.application.UserApplication;
import palaczjustyna.library.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowServiceTest {

    @Mock
    private BorrowRepository borrowRepository;

    @Mock
    private UserApplication userApplication;

    @Mock
    private BookApplication bookApplication;

    @Mock
    private EmailApplication emailApplication;

    @Mock
    private BorrowMapper borrowMapper;

    @InjectMocks
    private BorrowService borrowService;


    private final Integer id = 1;
    private final LocalDateTime dateOfBorrow = LocalDateTime.now();
    private final LocalDateTime dateOfReturn = LocalDateTime.now();
    private final String bookTitle = "Dziady";
    private final String bookAuthor = "Adam Mickiewicz";
    private final String firstNameUser = "Justyna";
    private final String lastNameUser = "Palacz";
    private final String emailUser = "justnapalacz@bilioteka.pl";

    @Test
    void shouldReturnAllBorrows() {
        //given
        Borrow borrow = createBorrow();
        borrow.setBook(createBook());
        borrow.setUser(createUser());
        BorrowDTO borrowDTO = createBorrowDTO();

        when(borrowRepository.findAll()).thenReturn(List.of(borrow));
        when(borrowMapper.mapToBarrowDTO(borrow)).thenReturn(borrowDTO);

        //when
        var result = borrowService.getAllBorrows();

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
    void shouldGetBarrowsDTOById() {
        //given
        Borrow borrow = createBorrow();
        Book book = createBook();
        borrow.setBook(book);

        User user = createUser();
        borrow.setUser(user);

        BorrowDTO borrowDTO = createBorrowDTO();

        when(borrowRepository.findById(id)).thenReturn(Optional.of(borrow));
        when(borrowMapper.mapToBarrowDTO(borrow)).thenReturn(borrowDTO);

        //when
        var result = borrowService.getBorrowsDTOById(id);

        //then
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
    void shouldReturnExceptionBorrowNotFound() {
        //given
        when(borrowRepository.findById(id)).thenReturn(Optional.empty());

        //when
        var exception = assertThrows(BorrowNotFoundException.class, () -> borrowService.getBorrowsById(id));

        //then
        assertEquals("The borrow with id = " + id + " is not exist.", exception.getMessage());
    }


    @Test
    void shouldAddBorrow() {
        // given
        Integer bookId = 1;
        Integer userId = 2;
        BorrowCreateDTO borrowCreateDTO = new BorrowCreateDTO(bookId, userId);
        User user = new User();
        Book book = createBook();

        when(userApplication.findById(borrowCreateDTO.userId())).thenReturn(user);
        when(bookApplication.findById(borrowCreateDTO.bookId())).thenReturn(book);

        // when
        var result = borrowService.addBorrow(borrowCreateDTO);

        // then
        assertNotNull(result);
        assertEquals("Successfully created borrow", result);
    }

    @Test
    void shouldUpdateBorrowAndReturnBook() {
        //given
        Borrow borrow = createBorrow();
        borrow.setDateOfReturn(null);
        Book book = createBook();
        borrow.setBook(book);
        User user = createUser();
        borrow.setUser(user);

        when(borrowRepository.findById(id)).thenReturn(Optional.of(borrow));

        //when
        var result = borrowService.updateBorrowAndReturnBook(borrow.getId());

        //then
        assertNotNull(result);
        assertEquals("Successfully return", result);
    }

    @Test
    void shouldNotReturnReturnedBook() {
        //given
        Borrow borrow = createBorrow();
        borrow.setBook(createBook());

        when(borrowRepository.findById(id)).thenReturn(Optional.of(borrow));

        //when
        var result = borrowService.updateBorrowAndReturnBook(borrow.getId());

        //then
        assertNotNull(result);
        assertEquals("Book already returned. Please select another borrowId.", result);
    }

    @Test
    void shouldChargePenalty() {
        // given
        org.springframework.test.util.ReflectionTestUtils.setField(borrowService, "penaltyRate", 0.20);
        org.springframework.test.util.ReflectionTestUtils.setField(borrowService, "allowedDays", 30);
        Borrow borrow = createBorrow();
        borrow.setDateOfReturn(null);
        borrow.setDateOfBorrow(LocalDateTime.now().minusDays(40));
        borrow.setBook(createBook());

        when(borrowRepository.findById(id)).thenReturn(Optional.of(borrow));

        // when
        var result = borrowService.chargePenalty(borrow.getId());

        // then
        assertNotNull(result);
        assertEquals("Cash penalty: 2.0", result);
    }

    @Test
    void shouldNotChargePenalty() {
        // given
        org.springframework.test.util.ReflectionTestUtils.setField(borrowService, "penaltyRate", 0.20);
        org.springframework.test.util.ReflectionTestUtils.setField(borrowService, "allowedDays", 30);
        Borrow borrow = createBorrow();
        borrow.setDateOfReturn(null);
        borrow.setDateOfBorrow(LocalDateTime.now().minusDays(20));
        borrow.setBook(createBook());

        when(borrowRepository.findById(id)).thenReturn(Optional.of(borrow));

        // when
        var result = borrowService.chargePenalty(borrow.getId());

        // then
        assertNotNull(result);
        assertEquals("The book is returned on time", result);
    }


    @Test
    void shouldSendEmails() {
        //given
        org.springframework.test.util.ReflectionTestUtils.setField(borrowService, "allowedDays", 30);
        Borrow borrow = createBorrow();
        borrow.setDateOfBorrow(LocalDateTime.now().minusDays(50));
        borrow.setDateOfReturn(null);
        borrow.setBook(createBook());
        borrow.setUser(createUser());
        BorrowDTO borrowDTO = createBorrowDTO();
        borrowDTO.setDateOfBorrow(LocalDateTime.now().minusDays(50));
        borrowDTO.setDateOfReturn(null);

        when(borrowRepository.findAll()).thenReturn(List.of(borrow));
        when(borrowMapper.mapToBarrowDTO(borrow)).thenReturn(borrowDTO);

        //when
        var result = borrowService.sendEmail();
        //then
        verify(emailApplication, times(1)).sendEmailToUser(any(), any(), any(), any());
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(id, result.get(0).getId());
        assertNull(result.get(0).getDateOfReturn());
        assertEquals(bookTitle, result.get(0).getBookTitle());
        assertEquals(bookAuthor, result.get(0).getBookAuthor());
        assertEquals(firstNameUser, result.get(0).getFirstNameUser());
        assertEquals(lastNameUser, result.get(0).getLastNameUser());
        assertEquals(emailUser, result.get(0).getEmailUser());
    }

    private BorrowDTO createBorrowDTO() {
        BorrowDTO borrowDTO = new BorrowDTO();
        borrowDTO.setId(id);
        borrowDTO.setDateOfBorrow(dateOfBorrow);
        borrowDTO.setDateOfReturn(dateOfReturn);
        borrowDTO.setBookTitle(bookTitle);
        borrowDTO.setBookAuthor(bookAuthor);
        borrowDTO.setFirstNameUser(firstNameUser);
        borrowDTO.setLastNameUser(lastNameUser);
        borrowDTO.setEmailUser(emailUser);
        return borrowDTO;
    }

    private User createUser() {
        User user = new User();
        user.setFirstName(firstNameUser);
        user.setLastName(lastNameUser);
        user.setEmail(emailUser);
        return user;
    }

    private Book createBook() {
        Book book = new Book();
        book.setTitle(bookTitle);
        book.setAuthor(bookAuthor);
        book.setStatus(true);
        return book;
    }

    private Borrow createBorrow() {
        Borrow borrow = new Borrow();
        borrow.setId(id);
        borrow.setDateOfBorrow(dateOfBorrow);
        borrow.setDateOfReturn(dateOfReturn);
        return borrow;
    }
}