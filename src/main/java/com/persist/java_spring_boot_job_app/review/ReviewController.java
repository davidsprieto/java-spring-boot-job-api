package com.persist.java_spring_boot_job_app.review;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ReviewController {
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> findAllReviews() {
        return reviewRepository.findAllReviews();
    }

    @GetMapping("/companies/{companyId}/reviews")
    public ResponseEntity<Object> findAllReviewsFromCompany(@PathVariable Long companyId) {
        return reviewRepository.findAllReviewsByCompanyId(companyId);
    }

    @PostMapping("/companies/{companyId}/reviews")
    public ResponseEntity<String> createReview(@PathVariable Long companyId, @RequestBody Review review) {
        return reviewRepository.createReview(companyId, review);
    }

    @GetMapping("/companies/{companyId}/reviews/{reviewId}")
    public ResponseEntity<Object> findReviewById(@PathVariable Long companyId, @PathVariable Long reviewId) {
        return reviewRepository.findReviewById(companyId, reviewId);
    }

    @PutMapping("/companies/{companyId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReviewById(@PathVariable Long companyId, @PathVariable Long reviewId, @RequestBody Review review) {
        return reviewRepository.updateReviewById(companyId, reviewId, review);
    }

    @DeleteMapping("/companies/{companyId}/reviews/{reviewId}")
    public ResponseEntity<String> updateReviewById(@PathVariable Long companyId, @PathVariable Long reviewId) {
        return reviewRepository.deleteReviewById(companyId, reviewId);
    }
}
