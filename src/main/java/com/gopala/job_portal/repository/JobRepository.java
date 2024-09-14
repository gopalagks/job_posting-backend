package com.gopala.job_portal.repository;

import com.gopala.job_portal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    @Query("SELECT j FROM Job j WHERE " +
            "(:keywords IS NULL OR " +
            "LOWER(j.title) LIKE LOWER(CONCAT('%', :keywords, '%')) OR " +
            "LOWER(j.description) LIKE LOWER(CONCAT('%', :keywords, '%'))) AND " +
            "(:location IS NULL OR LOWER(j.location) = LOWER(:location)) AND " +
            "(:requiredSkills IS NULL OR EXISTS (SELECT 1 FROM j.requiredSkills s WHERE s IN :requiredSkills))")
    List<Job> searchJobs(@Param("keywords") String keywords,
                         @Param("location") String location,
                         @Param("requiredSkills") List<String> requiredSkills);
}
