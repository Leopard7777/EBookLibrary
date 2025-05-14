package mini.ebooklibrary.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public void delete(User user) {
        if(user == null)
            throw new RuntimeException("No user");
        em.remove(user);
    }

}
