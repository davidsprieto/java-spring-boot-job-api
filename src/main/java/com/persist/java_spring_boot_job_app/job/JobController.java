package com.persist.java_spring_boot_job_app.job;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class JobController {
    private final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> findAllJobs() {
        return jobRepository.findAllJobs();
    }

    @GetMapping("/{companyId}/jobs")
    public ResponseEntity<Object> findAllJobsFromCompany(@PathVariable Long companyId) {
        return jobRepository.findAllJobsByCompanyId(companyId);
    }

    @PostMapping("/{companyId}/jobs")
    public ResponseEntity<String> createJob(@PathVariable Long companyId, @RequestBody Job job) {
        return jobRepository.createJob(companyId, job);
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Object> findJobById(@PathVariable Long jobId) {
        return jobRepository.findJobById(jobId);
    }

    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<String> updateJobById(@PathVariable Long jobId, @RequestBody Job job) {
        return jobRepository.updateJobById(jobId, job);
    }

    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long jobId) {
        return jobRepository.deleteJobById(jobId);
    }
}
