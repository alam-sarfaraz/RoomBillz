package com.inn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
	    info = @Info(
	        title = "RoomBillz Application",
	        description = "Welcome to ROOMBILLZ Application. Smart Room & Expense Split Manager",
	        version = "v1",
	        contact = @Contact(
	            name = "Sarfaraz Alam",
	            email = "sarfarazalam2702@gmail.com",
	            url = "https://www.linkedin.com/in/alam-sarfaraz/"
	        ),
	        license = @License(
	            name = "Apache 2.0",
	            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
	        )
	    )
	)
public class RoomBillzApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomBillzApplication.class, args);
		System.out.println("************************************************** RoomBillz Service Started Successfully ************************************************************");
	}

}
