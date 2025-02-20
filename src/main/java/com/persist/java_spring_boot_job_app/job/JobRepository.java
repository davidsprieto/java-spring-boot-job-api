package com.persist.java_spring_boot_job_app.job;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobRepository {
    ResponseEntity<List<Job>> findAllJobs();

    ResponseEntity<Object> findAllJobsByCompanyId(Long companyId);

    ResponseEntity<String> createJob(Long companyId, Job job);

    ResponseEntity<Object> findJobById(Long jobId);

    ResponseEntity<String> updateJobById(Long jobId, Job job);

    ResponseEntity<String> deleteJobById(Long jobId);
}
