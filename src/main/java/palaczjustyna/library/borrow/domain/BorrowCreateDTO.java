package palaczjustyna.library.borrow.domain;

import jakarta.validation.constraints.NotNull;

public record BorrowCreateDTO(
        @NotNull (message = "Book Id is required")
        Integer bookId,
        @NotNull (message = "User Id is required")
        Integer userId) {
}
