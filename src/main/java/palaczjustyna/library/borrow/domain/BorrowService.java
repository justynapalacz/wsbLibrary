package palaczjustyna.library.borrow.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.book.application.BookApplication;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.book.domain.BookUpdateDTO;
import palaczjustyna.library.borrow.infrastructure.BorrowRepository;
import palaczjustyna.library.user.application.UserApplication;
import palaczjustyna.library.user.domain.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private BookApplication bookApplication;

    @Autowired
    private BorrowMapper borrowMapper;

    public List<BorrowDTO> getAllBorrows() {
        return ((List<Borrow>) borrowRepository.findAll()).stream().map(barrow -> borrowMapper.mapToBarrowDTO(barrow)).toList();
    }

    public BorrowDTO getBorrowsDTOById(Integer id) {
        return borrowMapper.mapToBarrowDTO(this.getBorrowsById(id)) ;
    }

    public Borrow getBorrowsById(Integer id) {
        return  borrowRepository.findById(id).orElseThrow(()-> new BorrowNotFoundException("The borrow with id = " + id + " is not exist" ));
    }

    public String addBorrow(BorrowCreateDTO borrowCreateDTO) {
        User user = userApplication.findById(borrowCreateDTO.userId());
        Book book = bookApplication.findById(borrowCreateDTO.bookId());
        if(book.getStatus().equals(false)) {
            return "Book already borrowed. Please select another book.";
        }
        bookApplication.updateBook(new BookUpdateDTO(book.getId(), null, null, false));
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
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDate = borrow.getDateOfBorrow();
        long diff = ChronoUnit.DAYS.between(firstDate, now);
        if (diff > 30) {
            penalty = (diff - 30) * 0.20;
            return "Cash penalty: " + penalty;
        } else {
            return "The book was returned on time";
        }
    }
}
