package palaczjustyna.library.bookOrder.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.bookOrder.domain.BookOrderService;
import palaczjustyna.library.bookOrder.domain.BookToOrderDTO;

import java.util.List;

@Service
public class BookOrderApplication {

    @Autowired
    private BookOrderService bookOrderService;

    public String createBookOrder(List<BookToOrderDTO> bookToOrderDTOs){
       return bookOrderService.createBookOrder(bookToOrderDTOs);
    }
}
