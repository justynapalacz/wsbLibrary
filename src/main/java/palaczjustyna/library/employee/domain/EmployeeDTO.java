package palaczjustyna.library.employee.domain;

import palaczjustyna.library.security.SecurityRoles;

import java.time.LocalDate;

public record EmployeeDTO(
        Integer id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String login,
        String password,
        String email,
        SecurityRoles role
) {
}
