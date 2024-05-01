package palaczjustyna.library.user.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapperImpl userMapper;

    @Test
    void shouldMapToUserDTO(){
        //given
        Integer id = 1;
        String firstName = "Anna";
        String lastName = "Kowalska";
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        //when
        var result = userMapper.mapToUserDTO(user);

        //then
        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(firstName, result.firstName());
        assertEquals(lastName, result.lastName());
    }
    @Test
    void shouldMapToUserListDTO(){
        //given
        Integer id = 1;
        String firstName = "Anna";
        String lastName = "Kowalska";
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        List<User> userList = new ArrayList<>();
        userList.add(user);

        //when
        var result = userMapper.mapToUserListDTO(userList);

        //then
        assertNotNull(result);
        assertEquals(id, result.get(0).id());
        assertEquals(firstName, result.get(0).firstName());
        assertEquals(lastName, result.get(0).lastName());
    }

}
