package org.example.controller;

import org.example.model.Job;
import org.example.model.SearchHistory;
import org.example.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/jobs")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public Page<Job> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) List<String> type,
            @RequestParam(required = false) String userId,
            Pageable pageable
    ) {
        return searchService.searchJobs(title, city, userId, type, pageable);
    }

    @GetMapping
    public Page<Job> getAllJobs(Pageable pageable) {
        return searchService.getAllJobs(pageable);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Job> getById(@PathVariable String id) {
        Optional<Job> job = searchService.getById(id);
        return job.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/city")
    public Page<Job> cityJobs(
            @RequestParam String city,
            Pageable pageable
    ) {
        return searchService.jobsInCity(city, pageable);
    }

    @GetMapping("/history")
    public List<SearchHistory> history(@RequestParam String userId) {
        return searchService.recentSearches(userId);
    }

}
