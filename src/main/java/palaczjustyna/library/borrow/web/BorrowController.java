package palaczjustyna.library.borrow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import palaczjustyna.library.borrow.application.BorrowApplication;
import palaczjustyna.library.borrow.domain.BorrowCreateDTO;
import palaczjustyna.library.borrow.domain.BorrowDTO;
import palaczjustyna.library.borrow.domain.BorrowNotFoundException;

import java.util.List;

@RestController
public class BorrowController {

    @Autowired
    private BorrowApplication borrowApplication;

    @GetMapping("/getBorrows")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    List<BorrowDTO> getAllBorrows() {
        return borrowApplication.getAllBorrows();
    }

    @GetMapping("/getBorrowsById")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    BorrowDTO getBorrowsById(@RequestParam(value = "id")  Integer id) {
        return borrowApplication.getBorrowsById(id);
    }

    @PostMapping("/addBorrow")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    String addBorrow(@RequestBody BorrowCreateDTO borrowCreateDTO){
        return  borrowApplication.addBorrow(borrowCreateDTO);
    }
    @PutMapping ("/returnBook")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    String updateBorrowAndReturnBook(@RequestParam(value = "id") Integer borrowId ){
        return borrowApplication.updateBorrowAndReturnBook(borrowId);
    }
}