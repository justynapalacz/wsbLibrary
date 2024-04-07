package palaczjustyna.library.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import palaczjustyna.library.user.application.UserApplication;
import palaczjustyna.library.user.domain.UserDTO;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserApplication userApplication;

    @GetMapping("/getUsers")
    List<UserDTO> getAllUsers() {
        return userApplication.getAllUsers();
    }
}
