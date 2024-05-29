package palaczjustyna.library.bookOrder.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record BookToOrderDTO(
        @NotEmpty(message = "ISBN must be not empty")
        String bookIsbn,
        @Min(value =1,message = "Please, give the number of book, which you want to order")
        Integer quantity) {
}
