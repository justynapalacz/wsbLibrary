package palaczjustyna.library.user.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.user.domain.User;
import palaczjustyna.library.user.domain.UserDTO;
import palaczjustyna.library.user.domain.UserService;

import java.util.List;

@Slf4j
@Service
public class UserApplication {

    @Autowired
    private UserService userService;

    public List<UserDTO> getAllUsers() {
        log.info("Search all users");
        return userService.getAllUsers();
    }

    public User findById(Integer id) {
        return userService.findById(id);
    }

    public UserDTO updateUser(UserDTO user) {
        return userService.updateUser(user);
    }

    public User addUser(UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    public void deleteUser(Integer id) {
        userService.deleteUser(id);
    }

    public List<UserDTO> getUserByLastNameLike(String lastName) {
        return userService.getUserByLastNameLike(lastName);
    }
}
