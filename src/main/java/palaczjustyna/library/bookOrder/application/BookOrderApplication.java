package palaczjustyna.library.bookOrder.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.bookOrder.domain.BookOrderService;
import palaczjustyna.library.bookOrder.domain.BookToOrderDTO;

import java.util.List;

@Slf4j
@Service
public class BookOrderApplication {

    @Autowired
    private BookOrderService bookOrderService;

    public String createBookOrder(List<BookToOrderDTO> bookToOrderDTOs){
        log.info("Create book order. Book order list {}", bookToOrderDTOs);
       return bookOrderService.createBookOrder(bookToOrderDTOs);
    }
}
