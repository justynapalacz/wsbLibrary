package palaczjustyna.library.book.domain;

public record BookUpdateDTO(Integer id, String title, String author, String isbn, Boolean status) {
}
