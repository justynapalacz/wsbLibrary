package palaczjustyna.library.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.user.infrastructure.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        List<User> resultList = (List<User>) userRepository.findAll();
        return userMapper.mapToUserListDTO(resultList);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found for id: "+id));
    }

    public UserDTO updateUser(UserDTO userUpdate) {
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

    public User addUser(UserDTO userDTO) {

        return userRepository.save(new User(userDTO.firstName(), userDTO.lastName(), userDTO.dateOfBirth(), userDTO.login(), userDTO.password(), userDTO.email()));
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public List<UserDTO> getUserByLastNameLike(String lastName) {
        List <User> resultList = userRepository.findByLastNameLike("%"+lastName+ "%");
        return userMapper.mapToUserListDTO(resultList);
    }
}
