package mini.ebooklibrary.service;

import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.domain.History;
import mini.ebooklibrary.domain.Loan;
import mini.ebooklibrary.domain.Type;
import mini.ebooklibrary.domain.User;
import mini.ebooklibrary.repository.HistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    public void commit(Loan loan, Type type) {
        historyRepository.save(loan, type);
    }

    public List<History> search(Long userId) {
        return historyRepository.findByUserId(userId);
    }
}
