package com.persist.java_spring_boot_job_app.company;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService implements CompanyRepository {
    private final CompanyRepositoryJpa companyRepositoryJpa;

    public CompanyService(CompanyRepositoryJpa companyRepositoryJpa) {
        this.companyRepositoryJpa = companyRepositoryJpa;
    }

    @Override
    public ResponseEntity<List<Company>> findAllCompanies() {
        return ResponseEntity.ok(companyRepositoryJpa.findAll());
    }

    @Override
    public ResponseEntity<String> createCompany(Company company) {
        if (company != null) {
            companyRepositoryJpa.save(company);
            return ResponseEntity.status(HttpStatus.CREATED).body("Success: " + company.getName() + " company created.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: company is null.");
    }

    @Override
    public ResponseEntity<Object> findCompanyById(Long id) {
        Company company = companyRepositoryJpa.findById(id).orElse(null);
        if (company != null) {
            return ResponseEntity.status(HttpStatus.OK).body(company);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: company with id " + id + " not found.");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateCompanyById(Long id, Company company) {
        Optional<Company> companyOptional = companyRepositoryJpa.findById(id);
        if (companyOptional.isPresent()) {
            Company companyToUpdate = companyOptional.get();
            if (company.getName() != null && !company.getName().isBlank()) {
                companyToUpdate.setName(company.getName());
            }
            if (company.getDescription() != null && !company.getDescription().isBlank()) {
                companyToUpdate.setDescription(company.getDescription());
            }
            if (company.getJobs() != null && !company.getJobs().isEmpty()) {
                companyToUpdate.setJobs(company.getJobs());
            }
            companyRepositoryJpa.save(companyToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body("Success: company with id " + id + " updated.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: company with id " + id + " not found.");
    }

    @Override
    public ResponseEntity<String> deleteCompanyById(Long id) {
        Company company = companyRepositoryJpa.findById(id).orElse(null);
        if (company != null) {
            companyRepositoryJpa.delete(company);
            return ResponseEntity.status(HttpStatus.OK).body("Success: company with id " + id + " deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: company with id " + id + " not found.");
    }
}
