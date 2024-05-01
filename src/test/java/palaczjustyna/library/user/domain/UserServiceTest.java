package palaczjustyna.library.user.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.borrow.domain.Borrow;
import palaczjustyna.library.user.infrastructure.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    public void testShouldReturnAllUsers() {
        //given
        Integer id = 1;
        UserDTO userDTO = new UserDTO(id,null,null,null,null,null,null);
        User user = new User();
        user.setId(id);

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.mapToUserListDTO(List.of(user))).thenReturn(List.of(userDTO));
        //when
        var result = userService.getAllUsers();

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
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        //when
        var result = userService.findById(id);

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
        LocalDate dateOfBirth = LocalDate.parse("1980-01-01");
        String password = "jannowak";
        String email = "jan@wp.pl";
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setPassword(password);
        user.setEmail(email);
        UserDTO userDTO = new UserDTO(id, firstName, lastName,null,password,dateOfBirth,email );
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.mapToUserDTO(any())).thenReturn(userDTO);

        //when
        var result = userService.updateUser(userDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(firstName, result.firstName());
        assertEquals(lastName, result.lastName());
        assertEquals(password, result.password());
        assertEquals(dateOfBirth, result.dateOfBirth());
        assertEquals(email, result.email());
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

        Borrow borrow = new Borrow();
        Integer barrowId=100;
        borrow.setId(barrowId);
        List<Borrow> borrowList = new ArrayList<>();
        borrowList.add(borrow);
        user.setBorrowList(borrowList);

        when(userRepository.save(any())).thenReturn(user);

        //when
        var result = userService.addUser(userDTO);

        //then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(dateOfBirth, result.getDateOfBirth());
        assertEquals(login, result.getLogin());
        assertEquals(password, result.getPassword());
        assertEquals(email, result.getEmail());
        assertEquals(borrowList, result.getBorrowList());
        assertEquals(barrowId, result.getBorrowList().get(0).getId());
    }

    @Test
    public void testShouldDeleteUser(){
        //given
        Integer id = 1;

        //when
        userService.deleteUser(id);

        //then
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    public void testShouldReturnUserByLastName() {
        //given
        String lastName = "Nowak";
        User user = new User();
        user.setLastName(lastName);
        UserDTO userDTO = new UserDTO(null,null,lastName,null, null, null, null);
        when(userRepository.findByLastNameLike("%"+lastName+"%")).thenReturn(List.of(user));
        when(userMapper.mapToUserListDTO(List.of(user))).thenReturn(List.of(userDTO));

        //when
        var result = userService.getUserByLastNameLike(lastName);

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(lastName, result.get(0).lastName());
    }

    @Test
    public void shouldReturnUserNotFoundException() {
        //given
        Integer id = 1;
        UserDTO userDTO = new UserDTO(id, null, null, null, null, null, null);

        //when
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDTO));

        // then
        assertTrue(thrown.getMessage().contains("User not found for id: 1"));
    }
}
