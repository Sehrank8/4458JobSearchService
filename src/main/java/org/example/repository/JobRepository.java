package org.example.repository;

import org.example.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
    Page<Job> findByTitleContainingIgnoreCaseAndCityIgnoreCase(String title, String city, Pageable pageable);
    Page<Job> findByCityIgnoreCase(String city, Pageable pageable);

}
