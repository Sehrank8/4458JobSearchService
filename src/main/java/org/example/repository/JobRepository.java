package org.example.repository;

import org.example.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {
    Page<Job> findByTitleContainingIgnoreCaseAndCityContainingIgnoreCase(String title, String city, Pageable pageable);

    Page<Job> findByCityIgnoreCase(String city, Pageable pageable);

    Page<Job> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<Job> findTop10ByTitleStartingWithIgnoreCase(String titlePart);

    List<Job> findTop10ByCityStartingWithIgnoreCase(String cityPart);

    Page<Job> findByCityInAndTypeIn(List<String> cities, List<String> types, Pageable pageable);
}
