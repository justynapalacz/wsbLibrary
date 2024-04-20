package palaczjustyna.library.employee.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.employee.domain.Employee;
import palaczjustyna.library.employee.domain.EmployeeDTO;
import palaczjustyna.library.employee.domain.EmployeeService;
import palaczjustyna.library.security.SecurityRoles;

import java.util.List;

@Service
public class EmployeeApplication {
    @Autowired
    private EmployeeService employeeService;

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    public Employee addEmployee(EmployeeDTO employeeDTO) {
        return employeeService.addEmployee(employeeDTO);
    }

    public String deleteEmployee(Integer id) {
       return employeeService.deleteEmployee(id);
    }

    public Employee updateEmployee(EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(employeeDTO);
    }

    public List<Employee> findEmployeeByRole (SecurityRoles role){
        return employeeService.findEmployeeByRole(role);
    }
}
