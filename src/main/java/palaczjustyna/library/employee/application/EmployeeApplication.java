package palaczjustyna.library.employee.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.employee.domain.Employee;
import palaczjustyna.library.employee.domain.EmployeeDTO;
import palaczjustyna.library.employee.domain.EmployeeService;
import palaczjustyna.library.security.SecurityRoles;

import java.util.List;

@Slf4j
@Service
public class EmployeeApplication {
    @Autowired
    private EmployeeService employeeService;

    public List<Employee> getAllEmployees() {
        log.info("Get all employees.");
        return employeeService.getAllEmployees();
    }

    public Employee addEmployee(EmployeeDTO employeeDTO) {
        log.info("Add employee. EmployeeDTO : {}", employeeDTO);
        return employeeService.addEmployee(employeeDTO);
    }

    public void deleteEmployee(Integer id) {
        log.info("Delete employee. EmployeeId : {}", id);
        employeeService.deleteEmployee(id);
    }

    public Employee updateEmployee(EmployeeDTO employeeDTO) {
        log.info("Update employee. EmployeeDTO : {}", employeeDTO);
        return employeeService.updateEmployee(employeeDTO);
    }

    public List<Employee> findEmployeeByRole (SecurityRoles role){
        log.info("Find employees by role. SecurityRoles : {}", role);
        return employeeService.findEmployeeByRole(role);
    }
}
