package palaczjustyna.library.employee.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import palaczjustyna.library.AbstractIntegrationTest;
import palaczjustyna.library.employee.domain.Employee;
import palaczjustyna.library.employee.domain.EmployeeDTO;
import palaczjustyna.library.security.SecurityRoles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void shouldReturnEmployeeList() {
        //given
        restTemplate = restTemplate.withBasicAuth("root", "root");

        //when
        var result = this.restTemplate.getForObject("http://localhost:" + port + "/getEmployees",
                Employee[].class);

        //then
        assertNotNull(result);
        assertEquals(2, result.length);
    }

    @Test
    void shouldAddEmployee() {
        //given
        restTemplate = restTemplate.withBasicAuth("root", "root");

        EmployeeDTO employeeDTO = new EmployeeDTO(null, "Jan", "Kowalski", LocalDate.of(2000, 2, 4),
                "kowalski", "kowalski", "kowalski@gmail.com", SecurityRoles.READER);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EmployeeDTO> requestEntity =
                new HttpEntity<>(employeeDTO, headers);

        //when
        var result = this.restTemplate.postForObject("http://localhost:" + port + "/addEmployee", requestEntity,
                Employee.class);

        //then
        assertNotNull(result);
    }

    @Test
    void shouldDeleteEmployee() {
        //given
        restTemplate = restTemplate.withBasicAuth("root", "root");
        var employeeBeforeDelete = this.restTemplate.getForObject("http://localhost:" + port + "/getEmployees",
                Employee[].class);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/deleteEmployee")
                .queryParam("id", employeeBeforeDelete[0].getId());

        //when
        this.restTemplate.delete(builder.toUriString());

        //then
        var employeeAfterDelete = this.restTemplate.getForObject("http://localhost:" + port + "/getEmployees",
                Employee[].class);

        assertEquals(employeeBeforeDelete.length - 1, employeeAfterDelete.length);
    }

    @Test
    void shouldUpdateEmployee() {
        //given
        restTemplate = restTemplate.withBasicAuth("root", "root");
        var newFirsName = "TestKowalski";
        var employees = this.restTemplate.getForObject("http://localhost:" + port + "/getEmployees",
                Employee[].class);
        var employeeIdToUpdate = employees[0].getId();

        EmployeeDTO employeeDTO = new EmployeeDTO(employeeIdToUpdate, newFirsName, null, null,
                null, null, null, null);


        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EmployeeDTO> requestEntity =
                new HttpEntity<>(employeeDTO, headers);

        //when
        this.restTemplate.put("http://localhost:" + port + "/updateEmployee", requestEntity,
                Employee.class);

        //then
        var employeesAfterUpdate = this.restTemplate.getForObject("http://localhost:" + port + "/getEmployees",
                Employee[].class);

        assertNotNull(employeesAfterUpdate);
        assertEquals(employeeIdToUpdate, employeesAfterUpdate[0].getId());
        assertEquals(newFirsName, employeesAfterUpdate[0].getFirstName());
    }
}