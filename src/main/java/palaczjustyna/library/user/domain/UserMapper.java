package palaczjustyna.library.user.domain;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserDTO mapToUserDTO(User user);
    List<UserDTO> mapToUserListDTO (List<User> userList);
}
