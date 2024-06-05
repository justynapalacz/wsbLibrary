package palaczjustyna.library.employee.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import palaczjustyna.library.employee.domain.Employee;
import palaczjustyna.library.employee.domain.EmployeeDTO;
import palaczjustyna.library.employee.domain.EmployeeService;
import palaczjustyna.library.security.SecurityRoles;

import java.util.List;

/**
 * The EmployeeApplication class provides methods for managing employees.
 * It delegates the employee management functionality to the {@link EmployeeService}.
 *
 * <p>This class is annotated with {@link Service} to indicate that it is a service component
 * and {@link Slf4j} for logging purposes.</p>
 *
 * @see EmployeeService
 * @see EmployeeDTO
 * @see Employee
 * @see SecurityRoles
 */
@Slf4j
@Service
@AllArgsConstructor
public class EmployeeApplication {

    private final EmployeeService employeeService;

    /**
     * Retrieves all employees.
     *
     * @return a list of all employees
     */
    public List<Employee> getAllEmployees() {
        log.info("Get all employees.");
        return employeeService.getAllEmployees();
    }

    /**
     * Adds a new employee based on the provided {@link EmployeeDTO}.
     *
     * @param employeeDTO the {@link EmployeeDTO} object containing the details of the new employee
     * @return the newly added employee
     */
    public Employee addEmployee(EmployeeDTO employeeDTO) {
        log.info("Add employee. EmployeeDTO : {}", employeeDTO);
        return employeeService.addEmployee(employeeDTO);
    }

    /**
     * Deletes an employee based on the provided ID.
     *
     * @param id the ID of the employee to be deleted
     */
    public void deleteEmployee(Integer id) {
        log.info("Delete employee. EmployeeId : {}", id);
        employeeService.deleteEmployee(id);
    }

    /**
     * Updates an employee based on the provided {@link EmployeeDTO}.
     *
     * @param id the ID of the employee record to update.
     * @param employeeDTO the {@link EmployeeDTO} object containing the updated details of the employee
     * @return the updated employee
     */
    public Employee updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        log.info("Update employee. EmployeeDTO : {}", employeeDTO);
        return employeeService.updateEmployee(id, employeeDTO);
    }

    /**
     * Finds employees by their role.
     *
     * @param role the role of the employees to be found
     * @return a list of employees with the specified role
     */
    public List<Employee> findEmployeeByRole (SecurityRoles role){
        log.info("Find employees by role. SecurityRoles : {}", role);
        return employeeService.findEmployeeByRole(role);
    }
}
