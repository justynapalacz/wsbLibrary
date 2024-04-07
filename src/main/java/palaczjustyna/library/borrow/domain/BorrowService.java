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
import java.util.List;
import java.util.Optional;

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

    public BorrowDTO getBorrowsById(Integer id) {
        Optional<Borrow> borrow = borrowRepository.findById(id);
        if (borrow.isEmpty()) {
            throw new IllegalArgumentException("The borrow with id = " + id + " is not exist" );
        }
        return borrowMapper.mapToBarrowDTO(borrow.get()) ;
    }

    public void addBorrow(BorrowCreateDTO borrowCreateDTO) {
        User user = userApplication.findById(borrowCreateDTO.userId());
        Book book = bookApplication.findById(borrowCreateDTO.bookId());
        bookApplication.updateBook(new BookUpdateDTO(book.getId(), null, null, false));
        borrowRepository.save(new Borrow(user,book,LocalDateTime.now()));
    }
}
