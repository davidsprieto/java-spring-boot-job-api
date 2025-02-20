package com.persist.java_spring_boot_job_app.job;

import com.persist.java_spring_boot_job_app.company.Company;
import com.persist.java_spring_boot_job_app.company.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService implements JobRepository {
    private final JobRepositoryJpa jobRepositoryJpa;
    private final CompanyRepository companyRepository;

    public JobService(JobRepositoryJpa jobRepositoryJpa, CompanyRepository companyRepository) {
        this.jobRepositoryJpa = jobRepositoryJpa;
        this.companyRepository = companyRepository;
    }

    @Override
    public ResponseEntity<List<Job>> findAllJobs() {
        return ResponseEntity.ok(jobRepositoryJpa.findAll());
    }

    @Override
    public ResponseEntity<Object> findAllJobsByCompanyId(Long companyId) {
        ResponseEntity<Object> companyResponse = companyRepository.findCompanyById(companyId);
        if (companyResponse.getStatusCode().is2xxSuccessful()) {
            List<Job> jobsList = jobRepositoryJpa.findAllJobsByCompanyId(companyId);
            if (!jobsList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(jobsList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: no jobs for this company.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: company not found.");
    }

    @Override
    public ResponseEntity<String> createJob(Long companyId, Job job) {
        if (job != null) {
            ResponseEntity<Object> companyResponse = companyRepository.findCompanyById(companyId);
            if (companyResponse.getStatusCode().is2xxSuccessful()) {
                Company company = (Company) companyResponse.getBody();
                job.setCompany(company);
                jobRepositoryJpa.save(job);
                return ResponseEntity.status(HttpStatus.CREATED).body("Success: " + job.getTitle() + " job created.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: company not found.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: job is null.");
        }
    }

    @Override
    public ResponseEntity<Object> findJobById(Long id) {
        Job job = jobRepositoryJpa.findById(id).orElse(null);
        if (job != null) {
            return ResponseEntity.status(HttpStatus.OK).body(job);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: job with id " + id + " not found.");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateJobById(Long id, Job job) {
        Optional<Job> jobOptional = jobRepositoryJpa.findById(id);
        if (jobOptional.isPresent()) {
            Job jobToUpdate = jobOptional.get();
            if (job.getTitle() != null && !job.getTitle().isBlank()) {
                jobToUpdate.setTitle(job.getTitle());
            }
            if (job.getDescription() != null && !job.getDescription().isBlank()) {
                jobToUpdate.setDescription(job.getDescription());
            }
            if (job.getMinSalary() != null && !job.getMinSalary().isBlank()) {
                jobToUpdate.setMinSalary(job.getMinSalary());
            }
            if (job.getMaxSalary() != null && !job.getMaxSalary().isBlank()) {
                jobToUpdate.setMaxSalary(job.getMaxSalary());
            }
            if (job.getLocation() != null && !job.getLocation().isBlank()) {
                jobToUpdate.setLocation(job.getLocation());
            }
            if (job.getCompany() != null) {
                jobToUpdate.setCompany(job.getCompany());
            }
            jobRepositoryJpa.save(jobToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body("Success: job with id " + id + " updated.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: job with id " + id + " not found.");
    }

    @Override
    public ResponseEntity<String> deleteJobById(Long id) {
        Job job = jobRepositoryJpa.findById(id).orElse(null);
        if (job != null) {
            jobRepositoryJpa.delete(job);
            return ResponseEntity.status(HttpStatus.OK).body("Success: job with id " + id + " deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: job with id " + id + " not found.");
    }
}
