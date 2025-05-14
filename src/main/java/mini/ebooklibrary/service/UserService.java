package mini.ebooklibrary.service;

import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;



}
