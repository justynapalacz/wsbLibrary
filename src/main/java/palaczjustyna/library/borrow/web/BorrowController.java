package palaczjustyna.library.borrow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import palaczjustyna.library.borrow.application.BorrowApplication;
import palaczjustyna.library.borrow.domain.BorrowCreateDTO;
import palaczjustyna.library.borrow.domain.BorrowDTO;

import java.util.List;

@RestController
public class BorrowController {

    @Autowired
    private BorrowApplication borrowApplication;

    @GetMapping("/getBorrows")
    List<BorrowDTO> getAllBorrows() {
        return borrowApplication.getAllBorrows();
    }

    @GetMapping("/getBorrowsById")
    BorrowDTO getBorrowsById(@RequestParam(value = "id")  Integer id) {
        return borrowApplication.getBorrowsById(id);
    }

    @PostMapping("/addBorrow")
    String addBorrow(@RequestBody BorrowCreateDTO borrowCreateDTO){
        borrowApplication.addBorrow(borrowCreateDTO);
        return "Successfully created barrow";
    }


}