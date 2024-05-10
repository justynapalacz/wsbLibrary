package palaczjustyna.library.employee.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import palaczjustyna.library.mutationTest.DoNotMutate;
import palaczjustyna.library.security.SecurityRoles;

import java.time.LocalDate;

@DoNotMutate
@Entity
@Data
@Table(name = "employees")
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private SecurityRoles role;

    public Employee(String firstName, String lastName, LocalDate dateOfBirth, String login, String password, String email, SecurityRoles role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
