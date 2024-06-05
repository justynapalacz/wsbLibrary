package palaczjustyna.library.user.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import palaczjustyna.library.user.application.UserApplication;
import palaczjustyna.library.user.domain.User;
import palaczjustyna.library.user.domain.UserDTO;
import palaczjustyna.library.user.domain.UserNotFoundException;

import java.util.List;

/**
 * The UserController class defines REST endpoints related to user management.
 * It is annotated with {@link RestController} to indicate that it's a controller for handling RESTful requests,
 * and {@link Slf4j} for logging.
 */
@Slf4j
@RestController
@AllArgsConstructor
public class UserController {

    private final UserApplication userApplication;


    /**
     * Retrieves all users.
     *
     * @return a list of {@link UserDTO} representing all users
     */
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    List<UserDTO> getAllUsers() {
        log.info("Search all users");
        return userApplication.getAllUsers();
    }

    /**
     * Updates a user.
     *
     * @param id   the ID of the user to update
     * @param user the {@link UserDTO} object containing updated user information
     * @return the updated {@link UserDTO}
     */
    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_READER')")
    @ResponseStatus(HttpStatus.OK)
    UserDTO updateUser(@PathVariable (value = "id")  Integer id, @RequestBody UserDTO user){
        log.info("Update User. UserDTO: {}", user);
        return userApplication.updateUser(id, user);
    }

    /**
     * Adds a new user.
     *
     * @param userDTO the {@link UserDTO} object representing the user to add
     * @return the added {@link User}
     */
    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    User addUser(@RequestBody UserDTO userDTO){
        log.info("Add User. UserDTO: {}", userDTO);
        return userApplication.addUser(userDTO);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser (@PathVariable (value = "id")  Integer id){
        log.info("Delete user id : {}", id);
        userApplication.deleteUser(id);
    }

    /**
     * Retrieves users by last name.
     *
     * @param lastName the last name to search for
     * @return a list of {@link UserDTO} matching the last name
     */
    @GetMapping("/usersByLastName")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    List<UserDTO> getUserByLastNameLike(@RequestParam(value = "lastName")  String lastName){
        log.info("Get User by last name. Last name: {}", lastName);
        return userApplication.getUserByLastNameLike(lastName);
    }

    /**
     * Handles exceptions of type {@link UserNotFoundException}.
     *
     * @param ex the exception to handle
     * @return a {@link ResponseEntity} with status 404 (Not Found) and the exception message
     */
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
