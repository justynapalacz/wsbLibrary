package palaczjustyna.library.book.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface BookMapper {

  @Mapping(target = "status", expression = "java(mapToBookStatus(book.getStatus()))")
    BookDTO mapToBookDTO (Book book);

    List<BookDTO> mapToBookListDTO (List<Book> book);

    default String mapToBookStatus(Boolean status) {
         if(status) {
            return "Available";
        }
        return "Borrowed";
    }
}
