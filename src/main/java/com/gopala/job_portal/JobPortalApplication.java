package com.gopala.job_portal;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Job Portal API",
				version = "1.0",
				description = "This API manages job postings, allows users to search for jobs, and manage job applications.",
				contact = @Contact(name = "Gopala Kumar Singh", email = "gopala4uto@gmail.com", url = "https://www.linkedin.com/in/gopala-kumar-singh-5597971b8/"),
				license = @License(name = "Apache 2.0", url = "http://springdoc.org")
		)
)
public class JobPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplication.class, args);
	}

}
