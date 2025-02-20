package com.persist.java_spring_boot_job_app.company;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyRepository {
    ResponseEntity<List<Company>> findAllCompanies();

    ResponseEntity<String> createCompany(Company company);

    ResponseEntity<Object> findCompanyById(Long id);

    ResponseEntity<String> updateCompanyById(Long id, Company company);

    ResponseEntity<String> deleteCompanyById(Long id);
}
