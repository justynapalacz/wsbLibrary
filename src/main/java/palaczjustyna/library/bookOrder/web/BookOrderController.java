package palaczjustyna.library.bookOrder.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import palaczjustyna.library.bookOrder.application.BookOrderApplication;
import palaczjustyna.library.bookOrder.domain.BookToOrderDTO;

import java.util.List;

@Slf4j
@RestController
public class BookOrderController {

    @Autowired
    private BookOrderApplication bookOrderApplication;

    @PostMapping("/orderBooks")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String createBookOrder(@Valid @RequestBody List<BookToOrderDTO> bookOrderDTOs) {
        log.info("Create book order. Book order list {}", bookOrderDTOs);
        return bookOrderApplication.createBookOrder(bookOrderDTOs);
    }
}
