package org.example.service;

import org.example.model.Job;
import org.example.model.SearchHistory;
import org.example.repository.JobRepository;
import org.example.repository.SearchHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchService {
    private final JobRepository jobRepo;
    private final SearchHistoryRepository historyRepo;

    public SearchService(JobRepository jobRepo, SearchHistoryRepository historyRepo) {
        this.jobRepo = jobRepo;
        this.historyRepo = historyRepo;
    }
    public Page<Job> getAllJobs(Pageable pageable) {
        return jobRepo.findAll(pageable);
    }



    public Page<Job> searchJobs(String title, String city, String userId, List<String> types, Pageable pageable) {
        if (userId != null && (title != null || city != null)) {
            SearchHistory history = new SearchHistory();
            history.setUserId(userId);
            history.setTitle(title);
            history.setCity(city);
            history.setTimestamp(LocalDateTime.now());
            historyRepo.save(history);
        }

        // Smart search
        if (title != null && city != null) {
            return jobRepo.findByTitleContainingIgnoreCaseAndCityContainingIgnoreCase(title, city, pageable);
        } else if (title != null) {
            return jobRepo.findByTitleContainingIgnoreCase(title, pageable);
        } else if (city != null) {
            return jobRepo.findByCityIgnoreCase(city, pageable);
        } else {
            return jobRepo.findAll(pageable);
        }
    }

    public Page<Job> jobsInCity(String city, Pageable pageable) {
        return jobRepo.findByCityIgnoreCase(city, pageable);
    }

    public List<SearchHistory> recentSearches(String userId) {
        return historyRepo.findTop5ByUserIdOrderByTimestampDesc(userId);
    }
}
