package com.persist.java_spring_boot_job_app.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepositoryJpa extends JpaRepository<Job, Long> {
    List<Job> findAllJobsByCompanyId(Long companyId);
}
