package palaczjustyna.library.borrow.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BorrowNotFoundException extends RuntimeException {
    public BorrowNotFoundException(String s) {
        super(s);
    }
}
