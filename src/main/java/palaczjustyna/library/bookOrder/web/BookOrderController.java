package palaczjustyna.library.bookOrder.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import palaczjustyna.library.bookOrder.application.BookOrderApplication;
import palaczjustyna.library.bookOrder.domain.BookToOrderDTO;

@Slf4j
@RestController
public class BookOrderController {

    @Autowired
    private BookOrderApplication bookOrderApplication;

    @PostMapping("/orderBooks")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String orderBooks(@RequestBody BookToOrderDTO bookOrderDTO) {
        return bookOrderApplication.createBookOrder(bookOrderDTO.bookTitle(), bookOrderDTO.quantity());
    }
}
