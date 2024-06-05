package palaczjustyna.library.employee.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.employee.domain.Employee;
import palaczjustyna.library.employee.domain.EmployeeDTO;
import palaczjustyna.library.employee.domain.EmployeeService;
import palaczjustyna.library.security.SecurityRoles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeApplicationTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeApplication employeeApplication;

    @Test
    public void testShouldReturnAllEmployees() {
        //given
        Integer id = 1;
        Employee employee = new Employee();
        employee.setId(id);
        when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

        //when
        var result = employeeApplication.getAllEmployees();

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(id, result.get(0).getId());
    }

    @Test
    public void testShouldAddEmployee() {
        //given
        Integer id = 1;
        String firstName = "Jan";
        String lastName = "Nowak";
        LocalDate dateOfBirth = LocalDate.parse("1980-01-01");
        String login = "jannowak";
        String password = "jannowak";
        String email = "jan@wp.pl";
        SecurityRoles role = SecurityRoles.READER;
        EmployeeDTO employeeDTO = new EmployeeDTO(firstName, lastName, dateOfBirth, login, password, email, role);
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDateOfBirth(dateOfBirth);
        employee.setLogin(login);
        employee.setPassword(password);
        employee.setEmail(email);
        employee.setRole(role);
        when(employeeService.addEmployee(employeeDTO)).thenReturn(employee);

        //when
        var result = employeeApplication.addEmployee(employeeDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(dateOfBirth, result.getDateOfBirth());
        assertEquals(login, result.getLogin());
        assertEquals(password, result.getPassword());
        assertEquals(email, result.getEmail());
        assertEquals(role, result.getRole());
    }

    @Test
    public void testShouldDeleteEmployee() {
        //given
        Integer id = 1;

        //when
        employeeApplication.deleteEmployee(id);

        //then
        verify(employeeService, times(1)).deleteEmployee(id);
    }

    @Test
    public void testShouldUpdateEmployee() {
        //given
        Integer id = 1;
        String firstName = "Jan";
        String lastName = "Nowak";
        EmployeeDTO employeeDTO = new EmployeeDTO(firstName, lastName, null, null, null, null, null);
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        when(employeeService.updateEmployee(id, employeeDTO)).thenReturn(employee);

        //when
        var result = employeeApplication.updateEmployee(id, employeeDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
    }

    @Test
    public void testShouldReturnEmployeesByRoles() {
        //given
        SecurityRoles role = SecurityRoles.READER;
        Employee employee = new Employee();
        employee.setRole(role);
        when(employeeService.findEmployeeByRole(role)).thenReturn(List.of(employee));

        //when
        var result = employeeApplication.findEmployeeByRole(role);

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(role, result.get(0).getRole());
    }
}
