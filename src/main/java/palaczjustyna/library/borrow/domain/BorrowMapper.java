package palaczjustyna.library.borrow.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BorrowMapper {

    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "firstNameUser", source = "user.firstName")
    @Mapping(target = "lastNameUser", source = "user.lastName")
    BorrowDTO mapToBarrowDTO(Borrow borrow);
}
