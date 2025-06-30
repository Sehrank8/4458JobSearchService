package org.example.service;

import org.example.model.Job;
import org.example.model.SearchHistory;
import org.example.repository.JobRepository;
import org.example.repository.SearchHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


    public Page<Job> searchJobs(String title, String city, String userId, Pageable pageable) {
        SearchHistory hist = new SearchHistory();
        hist.setUserId(userId);
        hist.setCity(city);
        hist.setPosition(title);
        historyRepo.save(hist);

        return jobRepo.findByTitleContainingIgnoreCaseAndCityIgnoreCase(title, city, pageable);
    }

    public Page<Job> jobsInCity(String city, Pageable pageable) {
        return jobRepo.findByCityIgnoreCase(city, pageable);
    }

    public List<SearchHistory> recentSearches(String userId) {
        return historyRepo.findTop5ByUserIdOrderByTimestampDesc(userId);
    }
}
