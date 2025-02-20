package com.persist.java_spring_boot_job_app.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepositoryJpa extends JpaRepository<Review, Long> {
    List<Review> findAllReviewsByCompanyId(Long companyId);
}
