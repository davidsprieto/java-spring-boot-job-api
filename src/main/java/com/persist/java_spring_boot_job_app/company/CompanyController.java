package com.persist.java_spring_boot_job_app.company;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public ResponseEntity<List<Company>> findAllCompanies() {
        return companyRepository.findAllCompanies();
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        return companyRepository.createCompany(company);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findCompanyById(@PathVariable Long id) {
        return companyRepository.findCompanyById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompanyById(@PathVariable Long id, @RequestBody Company company) {
        return companyRepository.updateCompanyById(id, company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long id) {
        return companyRepository.deleteCompanyById(id);
    }
}
