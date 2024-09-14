package com.gopala.job_portal.controller;

import com.gopala.job_portal.entity.Job;
import com.gopala.job_portal.error.ErrorMessage;
import com.gopala.job_portal.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/job")
public class JobController {
    private JobService jobService;
    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // post
    @Operation(summary = "Add a new job posting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Job created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Job.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Job> addJob(@RequestBody Job job){
        Job createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @Operation(summary = "Get job posting by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the job",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Job.class)) }),
            @ApiResponse(responseCode = "404", description = "Job not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id){
        try {
            Job job = jobService.getJobById(id);
            return ResponseEntity.ok(job);
        }
        catch(NoSuchElementException e){
            ErrorMessage errorMessage = new ErrorMessage("Job with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }catch (Exception e) {
            // Handle other exceptions
            ErrorMessage errorMessage = new ErrorMessage("An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @Operation(summary = "Update job posting by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Job.class)) }),
            @ApiResponse(responseCode = "404", description = "Job not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobById(@PathVariable Long id,
                                @RequestBody Job updatedJob){
        try {
            Job existingJob = jobService.updateJob(id,updatedJob);
            return ResponseEntity.ok(existingJob);
        }
        catch(NoSuchElementException e){
            ErrorMessage errorMessage = new ErrorMessage("Job with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }catch (Exception e) {
            // Handle other exceptions
            ErrorMessage errorMessage = new ErrorMessage("An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @Operation(summary = "Get all job postings")
    @ApiResponse(responseCode = "200", description = "List of all jobs",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Job.class)) })

    @GetMapping
    public ResponseEntity<List<Job>> getAllJob(){
        List<Job> lsJob = jobService.getAllJobs();
        return ResponseEntity.ok(lsJob);
    }

    @Operation(summary = "Delete job posting by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Job not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }) })


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            jobService.deleteJob(id);
            return ResponseEntity.ok("Job with ID " + id + " deleted Successfully.");
        }
        catch(NoSuchElementException e){
            ErrorMessage errorMessage = new ErrorMessage("Job with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }catch (Exception e) {
            // Handle other exceptions
            ErrorMessage errorMessage = new ErrorMessage("An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }


    @Operation(summary = "Search job postings by keywords, location, and required skills")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Job.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })


    @GetMapping("/search")
    public ResponseEntity<?> searchJobs(
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) List<String> requiredSkills) {
        try {
            List<Job> jobs = jobService.searchJobs(keywords, location, requiredSkills);
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            // Handle other exceptions
            ErrorMessage errorMessage = new ErrorMessage("An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }




}
