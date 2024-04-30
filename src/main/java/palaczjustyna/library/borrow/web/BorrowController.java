package palaczjustyna.library.borrow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    List<BorrowDTO> getAllBorrows() {
        return borrowApplication.getAllBorrows();
    }

    @GetMapping("/getBorrowsById")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    @ResponseStatus(HttpStatus.OK)
    BorrowDTO getBorrowsById(@RequestParam(value = "id")  Integer id) {
        return borrowApplication.getBorrowsById(id);
    }

    @PostMapping("/addBorrow")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    String addBorrow(@RequestBody BorrowCreateDTO borrowCreateDTO){
        return  borrowApplication.addBorrow(borrowCreateDTO);
    }
    @PutMapping ("/returnBook")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    String updateBorrowAndReturnBook(@RequestParam(value = "id") Integer borrowId ){
        return borrowApplication.updateBorrowAndReturnBook(borrowId);
    }
    @GetMapping("/checkPenalty")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    String chargePenalty (@RequestParam(value = "id") Integer borrowId){
        return borrowApplication.chargePenalty(borrowId);
    }
    @GetMapping("/sendEmail")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    List<BorrowDTO> sendEmail (){
        return borrowApplication.sendEmail();
    }
}