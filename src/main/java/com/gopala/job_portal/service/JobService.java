package com.gopala.job_portal.service;

import com.gopala.job_portal.entity.Job;
import com.gopala.job_portal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobs(){

        return jobRepository.findAll();

    }
    public Job getJobById(Long id){
        return jobRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Job with id"+  id + " not available"));
    }
    public Job createJob(Job job){
        return jobRepository.save(job);
    }

    public void deleteJob(long id){
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Job with id " + id + " not found"));

        jobRepository.deleteById(id);
    }

    public Job updateJob(long id,Job updatedJob){
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Job with id " + id + " not found"));

        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setCompany(updatedJob.getCompany());
        existingJob.setSalaryRange(updatedJob.getSalaryRange());
        existingJob.setRequiredSkills(updatedJob.getRequiredSkills());

        return jobRepository.save(existingJob);
    }
    public List<Job> searchJobs(String keywords, String location, List<String> requiredSkills) {
        return jobRepository.searchJobs(keywords, location, requiredSkills);
    }

}
