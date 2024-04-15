package palaczjustyna.library.user.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.user.domain.User;
import palaczjustyna.library.user.domain.UserDTO;
import palaczjustyna.library.user.domain.UserService;
import palaczjustyna.library.user.domain.UserUpdateDTO;

import java.util.List;

@Service
public class UserApplication {

    @Autowired
    private UserService userService;

    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    public User findById(Integer id) {
        return userService.findById(id);
    }

    public UserDTO updateUser(UserUpdateDTO user) {
        return userService.updateUser(user);
    }
}
