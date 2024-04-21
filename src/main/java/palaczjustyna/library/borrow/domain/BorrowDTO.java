package palaczjustyna.library.borrow.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
public class BorrowDTO {
    private Integer id;
    private LocalDateTime dateOfBorrow;
    private LocalDateTime dateOfReturn;
    private String bookTitle;
    private String bookAuthor;
    private String firstNameUser;
    private String lastNameUser;
    private String emailUser;
}
