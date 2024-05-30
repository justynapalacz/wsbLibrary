package palaczjustyna.library.employee.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import palaczjustyna.library.AbstractIntegrationTest;
import palaczjustyna.library.employee.domain.Employee;
import palaczjustyna.library.employee.domain.EmployeeDTO;
import palaczjustyna.library.security.SecurityRoles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class EmployeeControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void shouldReturnEmployeeList() {
        //given
        //when
        var result = getAllEmployee();

        //then
        assertNotNull(result);
        assertEquals(2, result.length);
    }

    @Test
    void shouldAddEmployee() {
        //given
        EmployeeDTO employeeDTO = new EmployeeDTO(null, "Jan", "Kowalski", LocalDate.of(2000, 2, 4),
                "kowalski", "kowalski", "kowalski@gmail.com", SecurityRoles.READER);

        //when
        var result = WebClient.create("http://localhost:" + port )
                .method(HttpMethod.POST)
                .uri(builder -> builder.path("/employees").build())
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .body(BodyInserters.fromValue(employeeDTO))
                .retrieve()
                .bodyToMono(Employee.class)
                .block();

        //then
        assertNotNull(result);
    }

    @Test
    void shouldDeleteEmployee() {
        //given
        var employeeBeforeDelete = getAllEmployee();

        //when
        WebClient.create("http://localhost:" + port )
                .method(HttpMethod.DELETE)
                .uri(builder -> builder.path("/employees/{id}").build(employeeBeforeDelete[0].getId()))
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //then
        var employeeAfterDelete = getAllEmployee();

        assertEquals(employeeBeforeDelete.length - 1, employeeAfterDelete.length);
    }

    @Test
    void shouldUpdateEmployee() {
        //given
        var newFirsName = "TestKowalski";
        var employees = getAllEmployee();
        var employeeIdToUpdate = employees[0].getId();

        EmployeeDTO employeeDTO = new EmployeeDTO(employeeIdToUpdate, newFirsName, null, null,
                null, null, null, null);

        //when
        var result = WebClient.create("http://localhost:" + port )
                .method(HttpMethod.PUT)
                .uri(builder -> builder.path("/employees/{id}").build(employeeIdToUpdate))
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .body(BodyInserters.fromValue(employeeDTO))
                .retrieve()
                .bodyToMono(Employee.class)
                .block();

        //then
        var employeesAfterUpdate = getAllEmployee();

        assertNotNull(employeesAfterUpdate);
        assertEquals(employeeIdToUpdate, employeesAfterUpdate[0].getId());
        assertEquals(newFirsName, employeesAfterUpdate[0].getFirstName());
    }

    private Employee[] getAllEmployee() {
        return WebClient.create("http://localhost:" + port)
                .method(HttpMethod.GET)
                .uri(builder -> builder.path("/employees").build())
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(Employee[].class)
                .block();
    }
}