package mini.ebooklibrary.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager em;

    public void save(Book book) {
        em.persist(book);
    }

    public Book findOne(Long id) {
        return em.find(Book.class, id);
    }

    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class)
                .getResultList();
    }
}
