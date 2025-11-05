package com.inn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
	    info = @Info(
	        title = "RoomBillz Application",
	        version = "v1",
	        description = "Welcome to ROOMBILLZ Application — Smart Room & Expense Split Manager. This application helps users manage rooms, track expenses, and split bills seamlessly within groups.",
	        contact = @Contact(
	            name = "Sarfaraz Alam",
	            email = "sarfarazalam2702@gmail.com",
	            url = "https://www.linkedin.com/in/alam-sarfaraz/"
	        ),
	        license = @License(
	            name = "Apache 2.0",
	            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
	        )
	    ),
	    servers = {
	        @Server(
	            url = "http://localhost:8081/api/v1/roomBillz",
	            description = "Local Development Server"
	        ),
	        @Server(
		            url = "https://qa/api.roombillz.com/api/v1/roomBillz",
		            description = "QA Server"
		        ),
	        @Server(
	            url = "https://api.roombillz.com/api/v1/roomBillz",
	            description = "Production Server"
	        )
	    },
	    externalDocs = @ExternalDocumentation(
	        description = "RoomBillz API Reference and Documentation",
	        url = "https://roombillz.com/docs"
	    )
	)

public class RoomBillzApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomBillzApplication.class, args);
		System.out.println("************************************************** RoomBillz Service Started Successfully ************************************************************");
	}

}
