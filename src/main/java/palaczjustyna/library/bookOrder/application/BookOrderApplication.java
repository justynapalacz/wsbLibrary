package palaczjustyna.library.bookOrder.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.bookOrder.domain.BookOrderService;

@Service
public class BookOrderApplication {

    @Autowired
    private BookOrderService bookOrderService;

    public String createBookOrder(String bookTitle, Integer quantity){
       return bookOrderService.createBookOrder(bookTitle, quantity);
    }
}
