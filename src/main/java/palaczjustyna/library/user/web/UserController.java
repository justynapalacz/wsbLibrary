package palaczjustyna.library.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import palaczjustyna.library.user.application.UserApplication;
import palaczjustyna.library.user.domain.User;
import palaczjustyna.library.user.domain.UserDTO;

import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserApplication userApplication;

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    List<UserDTO> getAllUsers() {
        log.info("Search all users");
        return userApplication.getAllUsers();
    }

    @PutMapping("/updateUser")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    UserDTO updateUser(@RequestBody UserDTO user){
        return userApplication.updateUser(user);
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    User addUser(@RequestBody UserDTO userDTO){
        return userApplication.addUser(userDTO);
    }

    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void deleteUser (@RequestParam(value = "id")  Integer id){
        log.info("Delete user id : {}", id);
        userApplication.deleteUser(id);
    }

    @GetMapping("/getUserByLastName")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    List<UserDTO> getUserByLastNameLike(@RequestParam(value = "lastName")  String lastName){
        return userApplication.getUserByLastNameLike(lastName);
    }

}
