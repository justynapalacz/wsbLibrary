package palaczjustyna.library.employee.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import palaczjustyna.library.book.domain.BookNotFoundException;
import palaczjustyna.library.employee.application.EmployeeApplication;
import palaczjustyna.library.employee.domain.Employee;
import palaczjustyna.library.employee.domain.EmployeeDTO;
import palaczjustyna.library.employee.domain.EmployeeNotFoundException;

import java.util.List;

/**
 * The EmployeeController class provides RESTful APIs for managing employee records in the system.
 * It includes endpoints for retrieving, creating, updating, and deleting employee records.
 *
 * <p>This class is annotated with {@link RestController} to indicate that it is a web controller
 * and {@link Slf4j} for logging purposes.</p>
 *
 * <p>The controller uses {@link EmployeeApplication} to handle the business logic related to employee management.</p>
 *
 * @see EmployeeApplication
 * @see Employee
 * @see EmployeeDTO
 */
@Slf4j
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeApplication employeeApplication;

    /**
     * Retrieves all employee records.
     *
     * <p>This method is accessible only to users with the 'ROLE_ADMIN' authority.</p>
     *
     * @return a list of {@link Employee} objects representing all employee records.
     */
    @GetMapping("/employees")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    List<Employee> getAllEmployees() {
        log.info("Get all employees.");
        return employeeApplication.getAllEmployees();
    }

    /**
     * Creates a new employee record.
     *
     * <p>This method is accessible only to users with the 'ROLE_ADMIN' authority.</p>
     *
     * @param employeeDTO the {@link EmployeeDTO} object containing the details of the new employee.
     * @return the created {@link Employee} object.
     */
    @PostMapping("/employees")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    Employee addEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("Add employee. EmployeeDTO : {}", employeeDTO);
        return employeeApplication.addEmployee(employeeDTO);
    }

    /**
     * Deletes an employee record by its ID.
     *
     * <p>This method is accessible only to users with the 'ROLE_ADMIN' authority.</p>
     *
     * @param id the ID of the employee record to delete.
     */
    @DeleteMapping("/employees/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
     public void deleteEmployee (@PathVariable(value = "id")  Integer id){
        log.info("Delete employee. EmployeeId : {}", id);
        employeeApplication.deleteEmployee(id);
    }

    /**
     * Updates an existing employee record.
     *
     * <p>This method is accessible only to users with the 'ROLE_ADMIN' authority.</p>
     *
     * @param id the ID of the employee record to update.
     * @param employeeDTO the {@link EmployeeDTO} object containing the updated details of the employee.
     * @return the updated {@link Employee} object.
     */
    @PutMapping("/employees/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    Employee updateEmployee(@PathVariable(value = "id")  Integer id, @RequestBody EmployeeDTO employeeDTO){
        log.info("Update employee. EmployeeDTO : {}", employeeDTO);
        return employeeApplication.updateEmployee(employeeDTO);
    }

    /**
     * Exception handler method for handling {@link EmployeeNotFoundException}.
     * It returns a response entity with a not found status and the message from the exception.
     *
     * @param ex the {@link EmployeeNotFoundException} object thrown
     * @return a {@link ResponseEntity} with a not found status and the exception message as the body
     */
    @ExceptionHandler({EmployeeNotFoundException.class})
    public ResponseEntity<String> handleException(EmployeeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
