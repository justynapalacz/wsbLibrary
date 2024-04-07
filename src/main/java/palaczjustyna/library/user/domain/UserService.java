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
        return userRepository.findById(id).get();
    }
}
