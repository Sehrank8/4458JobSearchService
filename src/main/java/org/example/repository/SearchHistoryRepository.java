package org.example.repository;

import org.example.model.SearchHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SearchHistoryRepository extends MongoRepository<SearchHistory, String> {
    List<SearchHistory> findTop5ByUserIdOrderByTimestampDesc(String userId);
}

