package palaczjustyna.library.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import palaczjustyna.library.user.application.UserApplication;
import palaczjustyna.library.user.domain.UserDTO;
import palaczjustyna.library.user.domain.UserUpdateDTO;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserApplication userApplication;

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    List<UserDTO> getAllUsers() {
        return userApplication.getAllUsers();
    }

    @PutMapping("/updateUser")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    UserDTO updateUser(@RequestBody UserUpdateDTO user){
        return userApplication.updateUser(user);
    }
}
