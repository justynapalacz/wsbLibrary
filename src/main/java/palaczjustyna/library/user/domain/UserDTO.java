package palaczjustyna.library.user.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class UserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private LocalDate dateOfBirth;
}
