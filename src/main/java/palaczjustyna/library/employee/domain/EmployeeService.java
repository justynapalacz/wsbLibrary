package palaczjustyna.library.employee.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.employee.infrastructure.EmployeeRepository;
import palaczjustyna.library.security.SecurityRoles;

import java.util.List;

/**
 * The EmployeeService class provides methods for managing employee data.
 * It interacts with the underlying data storage through the {@link EmployeeRepository}.
 *
 * <p>This class is annotated with {@link Service} to indicate that it is a service component
 * and {@link Slf4j} for logging purposes.</p>
 *
 * @see EmployeeRepository
 * @see EmployeeDTO
 * @see Employee
 * @see SecurityRoles
 */
@Slf4j
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Retrieves all employees.
     *
     * @return a list of all employees
     */
    public List<Employee> getAllEmployees() {
        log.info("Get all employees.");
        return (List<Employee>) employeeRepository.findAll();
    }

    /**
     * Adds a new employee based on the provided {@link EmployeeDTO}.
     *
     * @param employeeDTO the {@link EmployeeDTO} object containing the details of the new employee
     * @return the newly added employee
     */
    public Employee addEmployee(EmployeeDTO employeeDTO) {
        log.info("Add employee. EmployeeDTO : {}", employeeDTO);
        return employeeRepository.save(new Employee(employeeDTO.firstName(), employeeDTO.lastName(), employeeDTO.dateOfBirth(),
                employeeDTO.login(), employeeDTO.password(), employeeDTO.email(), employeeDTO.role()));
    }

    /**
     * Deletes an employee based on the provided ID.
     *
     * @param id the ID of the employee to be deleted
     */
    public void deleteEmployee(Integer id) {
        log.info("Delete employee. EmployeeId : {}", id);
        employeeRepository.deleteById(id);
    }

    /**
     * Finds an employee by the provided ID.
     *
     * @param id the ID of the employee to be found
     * @return the employee with the specified ID
     * @throws EmployeeNotFoundException if no employee is found for the given ID
     */
    private Employee findById(Integer id) {
        log.info("Find employee by id. Id : {}", id);
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found for id: "+id));
    }


    /**
     * Updates an employee based on the provided {@link EmployeeDTO}.
     *
     * @param employeeDTO the {@link EmployeeDTO} object containing the updated details of the employee
     * @return the updated employee
     */
    public Employee updateEmployee(EmployeeDTO employeeDTO) {
        log.info("Update employee. EmployeeDTO : {}", employeeDTO);
        Employee employee = this.findById(employeeDTO.id());
        if (employeeDTO.firstName() != null) {
            employee.setFirstName(employeeDTO.firstName());
        }
        if (employeeDTO.lastName() != null) {
            employee.setLastName(employeeDTO.lastName());
        }
        if (employeeDTO.dateOfBirth() != null) {
            employee.setDateOfBirth(employeeDTO.dateOfBirth());
        }
        if (employeeDTO.password() != null) {
            employee.setPassword(employeeDTO.password());
        }
        if (employeeDTO.email() != null) {
            employee.setEmail(employeeDTO.email());
        }
        if (employeeDTO.role() != null) {
            employee.setRole(employeeDTO.role());
        }
        return employeeRepository.save(employee);
    }

    /**
     * Finds employees by their role.
     *
     * @param role the role of the employees to be found
     * @return a list of employees with the specified role
     */
    public List<Employee> findEmployeeByRole(SecurityRoles role) {
        log.info("Find employees by role. SecurityRoles : {}", role);
        return employeeRepository.findEmployeeByRole(role);
    }
}
