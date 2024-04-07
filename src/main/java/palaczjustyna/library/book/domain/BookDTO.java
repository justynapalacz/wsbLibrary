package palaczjustyna.library.book.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDTO {

    private Integer id;
    private String title;
    private String author;
    private Boolean status;
}
