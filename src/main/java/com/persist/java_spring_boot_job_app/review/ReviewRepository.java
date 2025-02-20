package com.persist.java_spring_boot_job_app.review;

import com.persist.java_spring_boot_job_app.job.Job;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewRepository {
    ResponseEntity<List<Review>> findAllReviews();

    ResponseEntity<Object> findAllReviewsByCompanyId(Long companyId);

    ResponseEntity<String> createReview(Long companyId, Review review);

    ResponseEntity<Object> findReviewById(Long companyId, Long reviewId);

    ResponseEntity<String> updateReviewById(Long companyId, Long reviewId, Review review);

    ResponseEntity<String> deleteReviewById(Long companyId, Long reviewId);
}
