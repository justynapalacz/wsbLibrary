package palaczjustyna.library.employee.infrastructure;

import org.springframework.data.repository.CrudRepository;
import palaczjustyna.library.employee.domain.Employee;
import palaczjustyna.library.security.SecurityRoles;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    List<Employee> findEmployeeByRole (SecurityRoles role);
}
