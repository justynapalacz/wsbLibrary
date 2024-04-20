package palaczjustyna.library.employee.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import palaczjustyna.library.employee.application.EmployeeApplication;
import palaczjustyna.library.employee.domain.Employee;
import palaczjustyna.library.employee.domain.EmployeeDTO;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeApplication employeeApplication;

    @GetMapping("/getEmployees")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    List<Employee> getAllEmployees() {
        return employeeApplication.getAllEmployees();
    }

    @PostMapping("/addEmployee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    Employee addEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeApplication.addEmployee(employeeDTO);
    }

    @DeleteMapping("/deleteEmployee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
     public String deleteEmployee (@RequestParam(value = "id")  Integer id){
        return employeeApplication.deleteEmployee(id);
    }

    @PutMapping("/updateEmployee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    Employee updateEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeApplication.updateEmployee(employeeDTO);
    }

}
