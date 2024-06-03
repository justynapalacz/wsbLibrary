package palaczjustyna.library.borrow.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.borrow.domain.BorrowCreateDTO;
import palaczjustyna.library.borrow.domain.BorrowDTO;
import palaczjustyna.library.borrow.domain.BorrowService;

import java.util.List;

/**
 * The BorrowApplication class provides methods for managing borrow-related operations.
 * It serves as an intermediary between the controller layer and the service layer.
 *
 * <p>This class is annotated with {@link Service} to indicate that it is a service component
 * and {@link Slf4j} for logging purposes.</p>
 *
 * @see BorrowService
 * @see BorrowDTO
 * @see BorrowCreateDTO
 */
@Slf4j
@Service
public class BorrowApplication {

    @Autowired
    private BorrowService borrowService;

    /**
     * Retrieves all borrows.
     *
     * @return a list of {@link BorrowDTO} objects representing all borrows.
     */
    public List<BorrowDTO> getAllBorrows() {
        log.info("Get all borrows");
        return borrowService.getAllBorrows();
    }

    /**
     * Retrieves a borrow by its ID.
     *
     * @param id the ID of the borrow to retrieve.
     * @return the {@link BorrowDTO} object with the specified ID.
     */
    public BorrowDTO getBorrowsById(Integer id) {
        log.info("Get borrows by Id. Id : {}", id);
        return borrowService.getBorrowsDTOById(id);
    }

    /**
     * Adds a new borrow.
     *
     * @param borrowCreateDTO the {@link BorrowCreateDTO} object containing the details of the new borrow.
     * @return a string message indicating the result of the borrow creation process.
     */
    public String addBorrow(BorrowCreateDTO borrowCreateDTO) {
        log.info("Add borrow. BorrowCreateDTO : {}", borrowCreateDTO);
        return borrowService.addBorrow(borrowCreateDTO);
    }

    /**
     * Updates a borrow and returns the book.
     *
     * @param borrowId the ID of the borrow to update.
     * @return a string message indicating the result of the borrow update process.
     */
    public String updateBorrowAndReturnBook(Integer borrowId) {
        log.info("Update borrow and return book for borrowId : {}", borrowId);
        return borrowService.updateBorrowAndReturnBook(borrowId);
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
        return borrowService.chargePenalty(borrowId);
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
        return borrowService.sendEmail();
    }
}
