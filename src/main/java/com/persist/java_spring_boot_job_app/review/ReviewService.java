package com.persist.java_spring_boot_job_app.review;

import com.persist.java_spring_boot_job_app.company.Company;
import com.persist.java_spring_boot_job_app.company.CompanyRepository;
import com.persist.java_spring_boot_job_app.job.Job;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements ReviewRepository {
    private final ReviewRepositoryJpa reviewRepositoryJpa;
    private final CompanyRepository companyRepository;

    public ReviewService(ReviewRepositoryJpa reviewRepositoryJpa, CompanyRepository companyRepository) {
        this.reviewRepositoryJpa = reviewRepositoryJpa;
        this.companyRepository = companyRepository;
    }

    @Override
    public ResponseEntity<List<Review>> findAllReviews() {
        return ResponseEntity.ok(reviewRepositoryJpa.findAll());
    }

    @Override
    public ResponseEntity<Object> findAllReviewsByCompanyId(Long companyId) {
        ResponseEntity<Object> companyResponse = companyRepository.findCompanyById(companyId);
        if (companyResponse.getStatusCode().is2xxSuccessful()) {
            List<Review> reviewsList = reviewRepositoryJpa.findAllReviewsByCompanyId(companyId);
            if (!reviewsList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(reviewsList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: no reviews for this company.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: company not found.");
    }

    @Override
    public ResponseEntity<String> createReview(Long companyId, Review review) {
        if (review != null) {
            ResponseEntity<Object> companyResponse = companyRepository.findCompanyById(companyId);
            if (companyResponse.getStatusCode().is2xxSuccessful()) {
                Company company = (Company) companyResponse.getBody();
                review.setCompany(company);
                reviewRepositoryJpa.save(review);
                return ResponseEntity.status(HttpStatus.CREATED).body("Success: " + review.getTitle() + " review created.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: company not found.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: review is null.");
        }
    }

    @Override
    public ResponseEntity<Object> findReviewById(Long companyId, Long reviewId) {
        Review review = reviewRepositoryJpa.findById(reviewId).orElse(null);
        if (review != null && review.getCompany().getId().equals(companyId)) {
            return ResponseEntity.status(HttpStatus.OK).body(review);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: review " + reviewId + " not found.");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateReviewById(Long companyId, Long reviewId, Review review) {
        Review reviewToUpdate = reviewRepositoryJpa.findById(reviewId).orElse(null);
        if (reviewToUpdate != null && reviewToUpdate.getCompany().getId().equals(companyId)) {
            if (review.getTitle() != null && !review.getTitle().isBlank()) {
                reviewToUpdate.setTitle(review.getTitle());
            }
            if (review.getContent() != null && !review.getContent().isBlank()) {
                reviewToUpdate.setContent(review.getContent());
            }
            if (review.getRating() != reviewToUpdate.getRating() && review.getRating() > 0d && review.getRating() <= 5d) {
                reviewToUpdate.setRating(review.getRating());
            }
            if (review.getCompany() != null) {
                reviewToUpdate.setCompany(review.getCompany());
            }
            reviewRepositoryJpa.save(reviewToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body("Success: review with id " + reviewId + " updated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: review with id " + reviewId + " not found.");
        }
    }

    @Override
    public ResponseEntity<String> deleteReviewById(Long companyId, Long reviewId) {
        Review review = reviewRepositoryJpa.findById(reviewId).orElse(null);
        if (review != null && review.getCompany().getId().equals(companyId)) {
            reviewRepositoryJpa.delete(review);
            return ResponseEntity.status(HttpStatus.OK).body("Success: review with id " + reviewId + " deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: review with id " + reviewId + " not found.");
        }
    }
}
