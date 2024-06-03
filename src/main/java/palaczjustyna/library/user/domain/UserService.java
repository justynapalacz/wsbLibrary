package palaczjustyna.library.user.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.user.infrastructure.UserRepository;

import java.util.List;

/**
 * The UserService class provides business logic for user-related operations.
 * It interacts with the UserRepository for data access and the UserMapper for mapping entities to DTOs.
 * This class is annotated with {@link Service} to mark it as a service component and {@link Slf4j} for logging.
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    /**
     * Retrieves all users.
     *
     * @return a list of {@link UserDTO} representing all users
     */
    public List<UserDTO> getAllUsers() {
        log.info("Search all users");
        List<User> resultList = (List<User>) userRepository.findAll();
        return userMapper.mapToUserListDTO(resultList);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link User} object corresponding to the given ID
     * @throws UserNotFoundException if the user with the specified ID is not found
     */
    public User findById(Integer id) {
        log.info("Find user by id. Id: {}", id);
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found for id: "+id));
    }

    /**
     * Updates a user.
     *
     * @param userUpdate the {@link UserDTO} object containing updated user information
     * @return the updated {@link UserDTO}
     */
    public UserDTO updateUser(UserDTO userUpdate) {
        log.info("Update User. UserDTO: {}", userUpdate);
        User user = this.findById(userUpdate.id());
        if (userUpdate.firstName() != null) {
            user.setFirstName(userUpdate.firstName());
        }
        if (userUpdate.lastName() != null) {
            user.setLastName(userUpdate.lastName());
        }
        if (userUpdate.dateOfBirth() != null) {
            user.setDateOfBirth(userUpdate.dateOfBirth());
        }
        if (userUpdate.password() != null) {
            user.setPassword(userUpdate.password());
        }
        if (userUpdate.email() != null) {
            user.setEmail(userUpdate.email());
        }
        return userMapper.mapToUserDTO(userRepository.save(user));
    }

    /**
     * Adds a new user.
     *
     * @param userDTO the {@link UserDTO} object representing the user to add
     * @return the added {@link User}
     */
    public User addUser(UserDTO userDTO) {
        log.info("Add User. UserDTO: {}", userDTO);
        return userRepository.save(new User(userDTO.firstName(), userDTO.lastName(), userDTO.dateOfBirth(), userDTO.login(), userDTO.password(), userDTO.email()));
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(Integer id) {
        log.info("Delete user id : {}", id);
        userRepository.deleteById(id);
    }

    /**
     * Retrieves users by last name.
     *
     * @param lastName the last name to search for
     * @return a list of {@link UserDTO} matching the last name
     */
    public List<UserDTO> getUserByLastNameLike(String lastName) {
        log.info("Get User by last name. Last name: {}", lastName);
        List <User> resultList = userRepository.findByLastNameLike("%"+lastName+ "%");
        return userMapper.mapToUserListDTO(resultList);
    }
}
