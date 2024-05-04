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

    public List<BorrowDTO> getAllBorrows() {
        return ((List<Borrow>) borrowRepository.findAll()).stream().map(barrow -> borrowMapper.mapToBarrowDTO(barrow)).toList();
    }

    public BorrowDTO getBorrowsDTOById(Integer id) {
        return borrowMapper.mapToBarrowDTO(this.getBorrowsById(id)) ;
    }

    public Borrow getBorrowsById(Integer id) {
        return  borrowRepository.findById(id).orElseThrow(()-> new BorrowNotFoundException("The borrow with id = " + id + " is not exist." ));
    }

    public String addBorrow(BorrowCreateDTO borrowCreateDTO) {
        User user = userApplication.findById(borrowCreateDTO.userId());
        Book book = bookApplication.findById(borrowCreateDTO.bookId());
        if(book.getStatus().equals(false)) {
            return "Book already borrowed. Please select another book.";
        }
        bookApplication.updateBook(new BookUpdateDTO(book.getId(), null, null, null, false));
        borrowRepository.save(new Borrow(user,book,LocalDateTime.now()));
        return "Successfully created borrow";
    }

    public String updateBorrowAndReturnBook(Integer borrowId) {
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

    public String chargePenalty(Integer borrowId) {
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

    public List<BorrowDTO> sendEmail() {
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
