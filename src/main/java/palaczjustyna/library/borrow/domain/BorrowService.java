package palaczjustyna.library.borrow.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import palaczjustyna.library.book.application.BookApplication;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.book.domain.BookUpdateDTO;
import palaczjustyna.library.borrow.infrastructure.BorrowRepository;
import palaczjustyna.library.email.application.EmailApplication;
import palaczjustyna.library.email.domain.EmailToSendDTO;
import palaczjustyna.library.user.application.UserApplication;
import palaczjustyna.library.user.domain.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The BorrowService class provides methods for managing borrow-related operations.
 * It interacts with the database and other services to perform borrow operations.
 *
 * <p>This class is annotated with {@link Service} to indicate that it is a service component
 * and {@link Slf4j} for logging purposes.</p>
 *
 * @see BorrowRepository
 * @see BorrowMapper
 * @see BookApplication
 * @see UserApplication
 * @see Borrow
 * @see BorrowCreateDTO
 * @see BorrowDTO
 * @see BorrowNotFoundException
 * @see Book
 * @see User
 */
@Service
@Slf4j
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private BookApplication bookApplication;

    @Autowired
    private EmailApplication emailApplication;

    @Autowired
    private BorrowMapper borrowMapper;

    @Value("${penalty.rate}")
    private double penaltyRate;
    @Value("${allowed.number.of.days}")
    private int allowedDays;
    @Value("${mail.from}")
    private String mailFrom;

    /**
     * Retrieves all borrows.
     *
     * @return a list of {@link BorrowDTO} objects representing all borrows.
     */
    public List<BorrowDTO> getAllBorrows() {
        log.info("Get all borrows");
        return ((List<Borrow>) borrowRepository.findAll()).stream().map(barrow -> borrowMapper.mapToBarrowDTO(barrow)).toList();
    }

    /**
     * Retrieves a borrow by its ID.
     *
     * @param id the ID of the borrow to retrieve.
     * @return the {@link BorrowDTO} object with the specified ID.
     * @throws BorrowNotFoundException if the borrow with the given ID does not exist.
     */
    public BorrowDTO getBorrowsDTOById(Integer id) {
        log.info("Get borrow dto by Id. Id : {}", id);
        return borrowMapper.mapToBarrowDTO(this.getBorrowsById(id)) ;
    }

    /**
     * Retrieves a borrow by its ID.
     *
     * @param id the ID of the borrow to retrieve.
     * @return the {@link Borrow} object with the specified ID.
     * @throws BorrowNotFoundException if the borrow with the given ID does not exist.
     */
    public Borrow getBorrowsById(Integer id) {
        log.info("Get borrow by Id. Id : {}", id);
        return  borrowRepository.findById(id).orElseThrow(()-> new BorrowNotFoundException("The borrow with id = " + id + " is not exist." ));
    }

    /**
     * Adds a new borrow.
     *
     * @param borrowCreateDTO the {@link BorrowCreateDTO} object containing the details of the new borrow.
     * @return a string message indicating the result of the borrow creation process.
     */
    public String addBorrow(BorrowCreateDTO borrowCreateDTO) {
        log.info("Add borrow. BorrowCreateDTO : {}", borrowCreateDTO);
        User user = userApplication.findById(borrowCreateDTO.userId());
        Book book = bookApplication.findById(borrowCreateDTO.bookId());
        if(book.getStatus().equals(false)) {
            return "Book already borrowed. Please select another book.";
        }
        bookApplication.updateBook(book.getId(), new BookUpdateDTO(null, null, null, false));
        borrowRepository.save(new Borrow(user,book,LocalDateTime.now()));
        return "Successfully created borrow";
    }

    /**
     * Updates a borrow and returns the book.
     *
     * @param borrowId the ID of the borrow to update.
     * @return a string message indicating the result of the borrow update process.
     */
    public String updateBorrowAndReturnBook(Integer borrowId) {
        log.info("Update borrow and return book for borrowId : {}", borrowId);
       Borrow borrow = this.getBorrowsById(borrowId);
       if(borrow.getDateOfReturn() != null) {
           return "Book already returned. Please select another borrowId.";
       }
       Book bookToReturn = borrow.getBook();
       bookApplication.returnBook(bookToReturn);
       borrow.setDateOfReturn(LocalDateTime.now());
       borrowRepository.save(borrow);
       return "Successfully return";
    }

    /**
     * Calculates and charges penalty for a borrow based on the allowed number of days and penalty rate.
     * If the book has already been returned, it returns a message indicating that the book was returned.
     * Otherwise, it calculates the penalty based on the difference between the current date and the borrow date.
     * If the difference exceeds the allowed number of days, it calculates the penalty as the difference multiplied by the penalty rate.
     * Otherwise, it returns a message indicating that the book is returned on time.
     *
     * @param borrowId the ID of the borrow for which penalty is to be charged.
     * @return a string message indicating the result of the penalty charging process.
     */
    public String chargePenalty(Integer borrowId) {
        log.info("Charge penalty for borrowId : {}", borrowId);
        double penalty;
        Borrow borrow = this.getBorrowsById(borrowId);
        if (borrow.getDateOfReturn() != null) {
            return "The book was returned.";
        } else {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime firstDate = borrow.getDateOfBorrow();
            long diff = ChronoUnit.DAYS.between(firstDate, now);
            if (diff > allowedDays) {
                penalty = (diff - allowedDays) * penaltyRate;
                return "Cash penalty: " + penalty;
            } else {
                return "The book is returned on time";
            }
        }
    }

    /**
     * Sends reminder emails to users for returning books that are overdue.
     * It retrieves all borrows, filters out the ones that are not returned and overdue,
     * and sends reminder emails to the corresponding users.
     *
     * @return a list of {@link BorrowDTO} objects representing the borrows for which emails were sent.
     */
    public List<BorrowDTO> sendEmail() {
        log.info("Send emails to users.");
        List<BorrowDTO> borrowsAfterTerminToSendEmail = getAllBorrows();
        borrowsAfterTerminToSendEmail = borrowsAfterTerminToSendEmail.stream()
                .filter(borrowDTO -> borrowDTO.getDateOfReturn() == null)
                .filter(borrowDTO -> ChronoUnit.DAYS.between(borrowDTO.getDateOfBorrow(), LocalDateTime.now()) > allowedDays)
                .collect(Collectors.toList());

        borrowsAfterTerminToSendEmail.forEach(borrowDTO -> {
            String from = mailFrom;
            String to =  borrowDTO.getEmailUser();
            String subject = "Return library books - reminder";
            String body =  "Dear " + borrowDTO.getFirstNameUser() + " " + borrowDTO.getLastNameUser()
                    + System.lineSeparator() +"We kindly inform you that the deadline for returning the book "
                    + borrowDTO.getBookTitle() + " by " + borrowDTO.getBookAuthor() + " has passed.";
            EmailToSendDTO emailToSendDTO = new EmailToSendDTO(from, to, subject, body);
            emailApplication.sendEmailToUser(emailToSendDTO);
        });
        return borrowsAfterTerminToSendEmail;
    }

}
