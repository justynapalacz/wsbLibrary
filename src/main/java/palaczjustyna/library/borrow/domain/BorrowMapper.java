package palaczjustyna.library.borrow.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BorrowMapper {

    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "bookAuthor", source = "book.author")
    @Mapping(target = "firstNameUser", source = "user.firstName")
    @Mapping(target = "lastNameUser", source = "user.lastName")
    @Mapping(target = "emailUser", source = "user.email")
    BorrowDTO mapToBarrowDTO(Borrow borrow);
}
