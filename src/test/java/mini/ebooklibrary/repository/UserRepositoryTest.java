package mini.ebooklibrary.repository;

import jakarta.persistence.EntityManager;
import mini.ebooklibrary.domain.Role;
import mini.ebooklibrary.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    @Test
    void save() {
        //given
        User user1 = new User("user", "1234", Role.USER);

        //when
        userRepository.save(user1);

        em.flush();
        em.clear(); // 영속성 컨텍스트 초기화

        //then
        Long findId = em.find(User.class, user1.getId()).getId();
        System.out.println("findId:" + findId);
        System.out.println("user1.getId():" + user1.getId());
        assertThat(user1.getId()).isEqualTo(findId);
    }

    @Test
    void findAll() {
        //given
        User user1 = new User("user1", "1234", Role.USER);
        User user2 = new User("user2", "1234", Role.USER);
        User user3 = new User("user3", "1234", Role.USER);

        //when
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        em.flush();
        em.clear(); // 영속성 컨텍스트 초기화

        //then
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(3);
    }

    @Test
    void delete() {
        //given
        User user1 = new User("user1", "1234", Role.USER);
        User user2 = new User("user2", "1234", Role.USER);
        userRepository.save(user1);
        userRepository.save(user2);

        em.flush();
        em.clear(); // 영속성 컨텍스트 초기화
        //when

        User find = userRepository.findById(user1.getId());
        userRepository.delete(find);
        em.flush();
        em.clear();

        //then
        User result = userRepository.findById(user1.getId());
        System.out.println("find = " + result);
        assertThat(result).isNull();


    }
}