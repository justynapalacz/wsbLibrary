package palaczjustyna.library.borrow.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import palaczjustyna.library.borrow.application.BorrowApplication;
import palaczjustyna.library.borrow.domain.BorrowCreateDTO;
import palaczjustyna.library.borrow.domain.BorrowDTO;
import palaczjustyna.library.borrow.domain.BorrowNotFoundException;

import java.util.List;

/**
 * The BorrowController class provides RESTful APIs for managing book borrow operations in the library system.
 * It includes endpoints for retrieving, creating, updating borrows, charging penalties, and sending notification emails.
 *
 * <p>This class is annotated with {@link RestController} to indicate that it is a web controller
 * and {@link Slf4j} for logging purposes.</p>
 *
 * <p>The controller uses {@link BorrowApplication} to handle the business logic related to borrows.</p>
 *
 * @see BorrowApplication
 * @see BorrowDTO
 * @see BorrowCreateDTO
 */
@Slf4j
@RestController
@AllArgsConstructor
public class BorrowController {

    private final BorrowApplication borrowApplication;

    /**
     * Retrieves all borrow records.
     *
     * <p>This method is accessible only to users with the 'ROLE_LIBRARIAN' authority.</p>
     *
     * @return a list of {@link BorrowDTO} objects representing all borrow records.
     */
    @GetMapping("/borrows")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    List<BorrowDTO> getAllBorrows() {
        log.info("Get all borrows");
        return borrowApplication.getAllBorrows();
    }

    /**
     * Retrieves a specific borrow record by its ID.
     *
     * <p>This method is accessible only to users with the 'ROLE_READER' authority.</p>
     *
     * @param id the ID of the borrow record to retrieve.
     * @return the {@link BorrowDTO} object representing the borrow record.
     */
    @GetMapping("/borrows/{id}")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    @ResponseStatus(HttpStatus.OK)
    BorrowDTO getBorrowsById(@PathVariable(value = "id")  Integer id) {
        log.info("Get borrows by Id. Id : {}", id);
        return borrowApplication.getBorrowsById(id);
    }

    /**
     * Creates a new borrow record.
     *
     * <p>This method is accessible only to users with the 'ROLE_LIBRARIAN' authority.</p>
     *
     * @param borrowCreateDTO the {@link BorrowCreateDTO} object containing the details of the new borrow.
     * @return a confirmation message indicating the borrow was created successfully.
     */
    @PostMapping("/borrows")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    String addBorrow(@Valid @RequestBody BorrowCreateDTO borrowCreateDTO){
        log.info("Add borrow. BorrowCreateDTO : {}", borrowCreateDTO);
        return borrowApplication.addBorrow(borrowCreateDTO);
    }

    /**
     * Updates a borrow record to indicate that the book has been returned.
     *
     * <p>This method is accessible only to users with the 'ROLE_LIBRARIAN' authority.</p>
     *
     * @param borrowId the ID of the borrow record to update.
     * @return a confirmation message indicating the borrow was updated successfully.
     */
    @PutMapping ("/borrows/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    String updateBorrowAndReturnBook(@PathVariable(value = "id") Integer borrowId ){
        log.info("Update borrow and return book for borrowId : {}", borrowId);
        return borrowApplication.updateBorrowAndReturnBook(borrowId);
    }

    /**
     * Calculates and charges penalty for a borrow based on the allowed number of days and penalty rate.
     * If the book has already been returned, it returns a message indicating that the book was returned.
     * Otherwise, it calculates the penalty based on the difference between the current date and the borrow date.
     * If the difference exceeds the allowed number of days, it calculates the penalty as the difference multiplied by the penalty rate.
     * Otherwise, it returns a message indicating that the book is returned on time.
     *
     * <p>This method is accessible only to users with the 'ROLE_LIBRARIAN' authority.</p>
     *
     * @param borrowId the ID of the borrow record for which to charge a penalty.
     * @return a confirmation message indicating the penalty was charged successfully.
     */
    @GetMapping("/checkPenalty")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    String chargePenalty (@RequestParam(value = "id") Integer borrowId){
        log.info("Charge penalty for borrowId : {}", borrowId);
        return borrowApplication.chargePenalty(borrowId);
    }

    /**
     * Sends reminder emails to users for returning books that are overdue.
     * It retrieves all borrows, filters out the ones that are not returned and overdue,
     * and sends reminder emails to the corresponding users.
     *
     * <p>This method is scheduled to run daily at midnight and is accessible only to users with the 'ROLE_LIBRARIAN' authority.</p>
     *
     * @return a list of {@link BorrowDTO} objects representing the borrows for which emails were sent.
     */
    @Scheduled(cron = "0 0 0 * * *")
    @GetMapping("/sendEmail")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    List<BorrowDTO> sendEmail (){
        log.info("Send emails to users.");
        return borrowApplication.sendEmail();
    }

    /**
     * Handles the {@link BorrowNotFoundException} exception.
     * Returns a response entity with HTTP status 404 (NOT_FOUND) and the exception message in the body.
     *
     * @param ex the {@link BorrowNotFoundException} exception to handle.
     * @return a {@link ResponseEntity} with HTTP status 404 and the exception message.
     */
    @ExceptionHandler({BorrowNotFoundException.class})
    public ResponseEntity<String> handleException(BorrowNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}