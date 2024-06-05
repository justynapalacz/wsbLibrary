package palaczjustyna.library.user.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import palaczjustyna.library.user.domain.User;
import palaczjustyna.library.user.domain.UserDTO;
import palaczjustyna.library.user.domain.UserService;

import java.util.List;

/**
 * The UserApplication class provides an application layer for user-related operations.
 * It serves as an intermediary between controllers and the UserService.
 * It is annotated with {@link Service} to indicate that it's a service component,
 * and {@link Slf4j} for logging.
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserApplication {

    private final UserService userService;

    /**
     * Retrieves all users.
     *
     * @return a list of {@link UserDTO} representing all users
     */
    public List<UserDTO> getAllUsers() {
        log.info("Search all users");
        return userService.getAllUsers();
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link User} object corresponding to the given ID
     */
    public User findById(Integer id) {
        log.info("Find user by id. Id: {}", id);
        return userService.findById(id);
    }

    /**
     * Updates a user.
     *
     * @param id   the ID of the user to update
     * @param user the {@link UserDTO} object containing updated user information
     * @return the updated {@link UserDTO}
     */
    public UserDTO updateUser(Integer id, UserDTO user) {
        log.info("Update User. UserDTO: {}", user);
        return userService.updateUser(id, user);
    }

    /**
     * Adds a new user.
     *
     * @param userDTO the {@link UserDTO} object representing the user to add
     * @return the added {@link User}
     */
    public User addUser(UserDTO userDTO) {
        log.info("Add User. UserDTO: {}", userDTO);
        return userService.addUser(userDTO);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(Integer id) {
        log.info("Delete user id : {}", id);
        userService.deleteUser(id);
    }

    /**
     * Retrieves users by last name.
     *
     * @param lastName the last name to search for
     * @return a list of {@link UserDTO} matching the last name
     */
    public List<UserDTO> getUserByLastNameLike(String lastName) {
        log.info("Get User by last name. Last name: {}", lastName);
        return userService.getUserByLastNameLike(lastName);
    }
}
