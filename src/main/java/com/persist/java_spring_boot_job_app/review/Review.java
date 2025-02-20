package com.persist.java_spring_boot_job_app.review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.persist.java_spring_boot_job_app.company.Company;
import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private double rating;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_id")
    private Company company;

    public Review() {
    }

    public Review(Long id, String title, String content, double rating) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rating = rating;
    }

    public Review(Long id, String title, String content, double rating, Company company) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
