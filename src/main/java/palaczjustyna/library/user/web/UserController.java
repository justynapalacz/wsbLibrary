package palaczjustyna.library.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    List<UserDTO> getAllUsers() {
        log.info("Search all users");
        return userApplication.getAllUsers();
    }

    @PutMapping("/updateUser")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    @ResponseStatus(HttpStatus.OK)
    UserDTO updateUser(@RequestBody UserDTO user){
        log.info("Update User. UserDTO: {}", user);
        return userApplication.updateUser(user);
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    User addUser(@RequestBody UserDTO userDTO){
        log.info("Add User. UserDTO: {}", userDTO);
        return userApplication.addUser(userDTO);
    }

    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser (@RequestParam(value = "id")  Integer id){
        log.info("Delete user id : {}", id);
        userApplication.deleteUser(id);
    }

    @GetMapping("/getUserByLastName")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    List<UserDTO> getUserByLastNameLike(@RequestParam(value = "lastName")  String lastName){
        log.info("Get User by last name. Last name: {}", lastName);
        return userApplication.getUserByLastNameLike(lastName);
    }
}
