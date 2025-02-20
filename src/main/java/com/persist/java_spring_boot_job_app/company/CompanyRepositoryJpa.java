package com.persist.java_spring_boot_job_app.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepositoryJpa extends JpaRepository<Company, Long> {
}
