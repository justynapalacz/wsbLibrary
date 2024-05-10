package palaczjustyna.library.employee.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.employee.infrastructure.EmployeeRepository;
import palaczjustyna.library.security.SecurityRoles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testShouldReturnAllEmployees() {
        //given
        Integer id = 1;
        Employee employee = new Employee();
        employee.setId(id);
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        //when
        var result = employeeService.getAllEmployees();

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
        EmployeeDTO employeeDTO = new EmployeeDTO(id, firstName, lastName, dateOfBirth, login, password, email, role);
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDateOfBirth(dateOfBirth);
        employee.setLogin(login);
        employee.setPassword(password);
        employee.setEmail(email);
        employee.setRole(role);
        when(employeeRepository.save(any())).thenReturn(employee);

        //when
        var result = employeeService.addEmployee(employeeDTO);

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
        employeeService.deleteEmployee(id);

        //then
        verify(employeeRepository, times(1)).deleteById(id);
    }

    @Test
    public void testShouldUpdateEmployee() {
        //given
        Integer id = 1;
        String firstName = "Jan";
        String lastName = "Nowak";
        LocalDate dateOfBirth = LocalDate.parse("1980-01-01");
        String password = "jannowak";
        String email = "jan@wp.pl";
        SecurityRoles role = SecurityRoles.READER;

        EmployeeDTO employeeDTO = new EmployeeDTO(id, firstName, lastName, dateOfBirth, null, password, email, role);

        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName("oldFirstname");
        employee.setLastName("oldLastName");
        employee.setDateOfBirth(LocalDate.parse("1975-01-01"));
        employee.setPassword("oldPassword");
        employee.setEmail("oldemail@o2.pl");
        employee.setRole(SecurityRoles.LIBRARIAN);
        ArgumentCaptor<Employee> argument = ArgumentCaptor.forClass(Employee.class);

        when(employeeRepository.save(any())).thenReturn(employee);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        //when
        var result = employeeService.updateEmployee(employeeDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(dateOfBirth, result.getDateOfBirth());
        assertEquals(password, result.getPassword());
        assertEquals(email, result.getEmail());
        assertEquals(role, result.getRole());

        verify(employeeRepository).save(argument.capture());
        assertEquals(firstName, argument.getValue().getFirstName());
        assertEquals(lastName, argument.getValue().getLastName());
        assertEquals(dateOfBirth, argument.getValue().getDateOfBirth());
        assertEquals(password, argument.getValue().getPassword());
        assertEquals(email, argument.getValue().getEmail());
        assertEquals(role, argument.getValue().getRole());
    }

    @Test
    public void shouldReturnExceptionEmployeeByIdNotFound() {
        //given
        Integer id = 1;
        EmployeeDTO employeeDTO = new EmployeeDTO(id, null, null, null, null, null, null, null);

        //when
        EmployeeNotFoundException thrown = assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(employeeDTO));

        // then
        assertTrue(thrown.getMessage().contains("Employee not found for id: 1"));
    }

    @Test
    public void testShouldReturnEmployeesByRoles() {
        //given
        SecurityRoles role = SecurityRoles.READER;
        Employee employee = new Employee();
        employee.setRole(role);
        when(employeeRepository.findEmployeeByRole(role)).thenReturn(List.of(employee));

        //when
        var result = employeeService.findEmployeeByRole(role);

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(role, result.get(0).getRole());
    }
}
