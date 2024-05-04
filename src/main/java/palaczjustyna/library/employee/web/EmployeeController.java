package palaczjustyna.library.employee.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import palaczjustyna.library.employee.application.EmployeeApplication;
import palaczjustyna.library.employee.domain.Employee;
import palaczjustyna.library.employee.domain.EmployeeDTO;

import java.util.List;

@Slf4j
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeApplication employeeApplication;

    @GetMapping("/getEmployees")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    List<Employee> getAllEmployees() {
        log.info("Get all employees.");
        return employeeApplication.getAllEmployees();
    }

    @PostMapping("/addEmployee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    Employee addEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("Add employee. EmployeeDTO : {}", employeeDTO);
        return employeeApplication.addEmployee(employeeDTO);
    }

    @DeleteMapping("/deleteEmployee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
     public void deleteEmployee (@RequestParam(value = "id")  Integer id){
        log.info("Delete employee. EmployeeId : {}", id);
        employeeApplication.deleteEmployee(id);
    }

    @PutMapping("/updateEmployee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    Employee updateEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("Update employee. EmployeeDTO : {}", employeeDTO);
        return employeeApplication.updateEmployee(employeeDTO);
    }

}
