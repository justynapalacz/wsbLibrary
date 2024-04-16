package palaczjustyna.library.user.domain;

import java.time.LocalDate;

public record UserUpdateDTO(Integer id, String firstName, String lastName, LocalDate dateOfBirth, String login, String password) {
}
