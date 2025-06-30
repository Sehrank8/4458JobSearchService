package org.example.controller;

import org.example.model.Job;
import org.example.model.SearchHistory;
import org.example.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public Page<Job> search(
            @RequestParam String title,
            @RequestParam String city,
            @RequestParam String userId,
            Pageable pageable
    ) {
        return searchService.searchJobs(title, city, userId, pageable);
    }

    @GetMapping
    public Page<Job> getAllJobs(Pageable pageable) {
        return searchService.getAllJobs(pageable);
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
