package palaczjustyna.library.book.domain;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    BookDTO mapToBookDTO (Book book);

    List<BookDTO> mapToBookListDTO (List<Book> book);
}
