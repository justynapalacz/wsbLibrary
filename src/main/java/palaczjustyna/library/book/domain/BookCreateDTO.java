package palaczjustyna.library.book.domain;

import jakarta.validation.constraints.NotEmpty;

public record BookCreateDTO(
        @NotEmpty(message = "Title must be not empty")
        String title,
        @NotEmpty(message = "Author must be not empty")
        String author,
        @NotEmpty(message = "ISBN must be not empty")
        String isbn) {
}


