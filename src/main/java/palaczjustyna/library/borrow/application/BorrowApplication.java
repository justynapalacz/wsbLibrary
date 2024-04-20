package palaczjustyna.library.borrow.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.borrow.domain.BorrowCreateDTO;
import palaczjustyna.library.borrow.domain.BorrowDTO;
import palaczjustyna.library.borrow.domain.BorrowService;

import java.util.List;

@Service
public class BorrowApplication {

    @Autowired
    private BorrowService borrowService;

    public List<BorrowDTO> getAllBorrows() {
        return borrowService.getAllBorrows();
    }

    public BorrowDTO getBorrowsById(Integer id) {
        return borrowService.getBorrowsDTOById(id);
    }

    public String addBorrow(BorrowCreateDTO borrowCreateDTO) {
        return borrowService.addBorrow(borrowCreateDTO);
    }

    public String updateBorrowAndReturnBook(Integer borrowId) {
        return borrowService.updateBorrowAndReturnBook(borrowId);
    }
}
