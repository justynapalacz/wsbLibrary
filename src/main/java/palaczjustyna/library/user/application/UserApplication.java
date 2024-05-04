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
        log.info("Find user by id. Id: {}", id);
        return userService.findById(id);
    }

    public UserDTO updateUser(UserDTO user) {
        log.info("Update User. UserDTO: {}", user);
        return userService.updateUser(user);
    }

    public User addUser(UserDTO userDTO) {
        log.info("Add User. UserDTO: {}", userDTO);
        return userService.addUser(userDTO);
    }

    public void deleteUser(Integer id) {
        log.info("Delete user id : {}", id);
        userService.deleteUser(id);
    }

    public List<UserDTO> getUserByLastNameLike(String lastName) {
        log.info("Get User by last name. Last name: {}", lastName);
        return userService.getUserByLastNameLike(lastName);
    }
}
