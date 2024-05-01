package palaczjustyna.library.employee.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.employee.infrastructure.EmployeeRepository;
import palaczjustyna.library.security.SecurityRoles;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    public Employee addEmployee(EmployeeDTO employeeDTO) {
        return employeeRepository.save(new Employee(employeeDTO.firstName(), employeeDTO.lastName(), employeeDTO.dateOfBirth(),
                employeeDTO.login(), employeeDTO.password(), employeeDTO.email(), employeeDTO.role()));
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
    private Employee findById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found for id: "+id));
    }

    public Employee updateEmployee(EmployeeDTO employeeDTO) {
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

    public List<Employee> findEmployeeByRole(SecurityRoles role) {
        return employeeRepository.findEmployeeByRole(role);
    }
}
