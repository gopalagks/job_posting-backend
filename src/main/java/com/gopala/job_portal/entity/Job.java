package com.gopala.job_portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Table(name = "job_postings")
@Entity
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobid;
    private String title;
    private String description;
    private String location;
    private String company;
    private String salaryRange;
    // This is used to store the list of skills as a collection in the database
    @ElementCollection
    @CollectionTable(name = "job_required_skills", joinColumns = @JoinColumn(name = "job_jobid"))
    @Column(name = "required_skills")
    private List<String> requiredSkills;

}
