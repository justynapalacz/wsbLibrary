package palaczjustyna.library.user.domain;

import java.time.LocalDate;

public record UserDTO(
        Integer id,
        String firstName,
        String lastName,
        String login,
        String password,
        LocalDate dateOfBirth
) {
}
