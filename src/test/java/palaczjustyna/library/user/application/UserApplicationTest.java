package palaczjustyna.library.user.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.user.domain.User;
import palaczjustyna.library.user.domain.UserDTO;
import palaczjustyna.library.user.domain.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserApplicationTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private  UserApplication userApplication;

    @Test
    public void testShouldReturnAllUsers() {
        //given
        Integer id = 1;
        UserDTO userDTO = new UserDTO(id,null,null,null,null,null,null);
        when(userService.getAllUsers()).thenReturn(List.of(userDTO));

        //when
        var result = userApplication.getAllUsers();

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(id, result.get(0).id());
    }

    @Test
    public void testShouldFindUserById() {
        //given
        Integer id = 1;
        User user = new User();
        user.setId(id);
        when(userService.findById(id)).thenReturn(user);

        //when
        var result = userApplication.findById(id);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void testShouldUpdateUser() {
        //given
        Integer id = 1;
        String firstName = "Jan";
        String lastName = "Nowak";
        UserDTO userDTO = new UserDTO(id, firstName, lastName,null,null,null,null );
        when(userService.updateUser(id, userDTO)).thenReturn(userDTO);

        //when
        var result = userApplication.updateUser(id, userDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(firstName, result.firstName());
        assertEquals(lastName, result.lastName());
    }

    @Test
    public void testShouldAddUser() {
        //given
        Integer id = 1;
        String firstName = "Jan";
        String lastName = "Nowak";
        LocalDate dateOfBirth = LocalDate.parse("1980-01-01");
        String login = "jannowak";
        String password = "jannowak";
        String email = "jan@wp.pl";
        UserDTO userDTO = new UserDTO(id,firstName,lastName,login,password,dateOfBirth,email);
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        when(userService.addUser(userDTO)).thenReturn(user);

        //when
        var result = userApplication.addUser(userDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(dateOfBirth, result.getDateOfBirth());
        assertEquals(login, result.getLogin());
        assertEquals(password, result.getPassword());
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testShouldDeleteUser(){
        //given
        Integer id = 1;

        //when
        userApplication.deleteUser(id);

        //then
        verify(userService, times(1)).deleteUser(id);
    }

    @Test
    public void testShouldReturnUserByLastName() {
        //given
        String lastName = "Nowak";
        UserDTO userDTO = new UserDTO(null,null,lastName,null, null, null, null);
        when(userService.getUserByLastNameLike(lastName)).thenReturn(List.of(userDTO));

        //when
        var result = userApplication.getUserByLastNameLike(lastName);

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(lastName, result.get(0).lastName());
    }
}
