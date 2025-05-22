package mini.ebooklibrary.service;

import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.domain.User;
import mini.ebooklibrary.repository.UserRepository;
import mini.ebooklibrary.service.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void join(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        // no user with parameter username;
        throw new UserNotFoundException();
    }
}
