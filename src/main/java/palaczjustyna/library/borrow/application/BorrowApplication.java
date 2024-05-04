package palaczjustyna.library.borrow.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.borrow.domain.BorrowCreateDTO;
import palaczjustyna.library.borrow.domain.BorrowDTO;
import palaczjustyna.library.borrow.domain.BorrowService;

import java.util.List;

@Slf4j
@Service
public class BorrowApplication {

    @Autowired
    private BorrowService borrowService;

    public List<BorrowDTO> getAllBorrows() {
        log.info("Get all borrows");
        return borrowService.getAllBorrows();
    }

    public BorrowDTO getBorrowsById(Integer id) {
        log.info("Get borrows by Id. Id : {}", id);
        return borrowService.getBorrowsDTOById(id);
    }

    public String addBorrow(BorrowCreateDTO borrowCreateDTO) {
        log.info("Add borrow. BorrowCreateDTO : {}", borrowCreateDTO);
        return borrowService.addBorrow(borrowCreateDTO);
    }

    public String updateBorrowAndReturnBook(Integer borrowId) {
        log.info("Update borrow and return book for borrowId : {}", borrowId);
        return borrowService.updateBorrowAndReturnBook(borrowId);
    }

    public String chargePenalty(Integer borrowId) {
        log.info("Charge penalty for borrowId : {}", borrowId);
        return borrowService.chargePenalty(borrowId);
    }

    public List<BorrowDTO> sendEmail() {
        log.info("Send emails to users.");
        return borrowService.sendEmail();
    }
}
