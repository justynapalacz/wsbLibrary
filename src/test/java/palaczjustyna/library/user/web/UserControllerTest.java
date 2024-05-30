package palaczjustyna.library.user.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.user.application.UserApplication;
import palaczjustyna.library.user.domain.User;
import palaczjustyna.library.user.domain.UserDTO;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserApplication userApplication;
    @InjectMocks
    private UserController userController;

    @Test
    public void testShouldReturnAllUsers() {
        //given
        Integer id = 1;
        UserDTO userDTO = new UserDTO(id,null,null,null,null,null,null);
        when(userApplication.getAllUsers()).thenReturn(List.of(userDTO));

        //when
        var result = userController.getAllUsers();

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(id, result.get(0).id());
    }
    @Test
    public void testShouldUpdateUser() {
        //given
        Integer id = 1;
        String firstName = "Jan";
        String lastName = "Nowak";
        UserDTO userDTO = new UserDTO(id, firstName, lastName,null,null,null,null );
        when(userApplication.updateUser(userDTO)).thenReturn(userDTO);

        //when
        var result = userController.updateUser(id, userDTO);

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
        when(userApplication.addUser(userDTO)).thenReturn(user);

        //when
        var result = userController.addUser(userDTO);

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
        userController.deleteUser(id);

        //then
        verify(userApplication, times(1)).deleteUser(id);
    }

    @Test
    public void testShouldReturnUserByLastName() {
        //given
        String lastName = "Nowak";
        UserDTO userDTO = new UserDTO(null,null,lastName,null, null, null, null);
        when(userApplication.getUserByLastNameLike(lastName)).thenReturn(List.of(userDTO));

        //when
        var result = userController.getUserByLastNameLike(lastName);

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(lastName, result.get(0).lastName());
    }
}
