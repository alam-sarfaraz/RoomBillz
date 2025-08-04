package com.inn.logs;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "API_LOG")
@Data
public class ApiLog {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID")
	    private Long id;

	    @Column(name = "URL", length = 1000)
	    private String url;

	    @Column(name = "HTTP_METHOD", length = 20)
	    private String httpMethod;

	    @Lob
	    @Column(name = "REQUEST_BODY", columnDefinition = "LONGTEXT")
	    private String requestBody;

	    @Lob
	    @Column(name = "RESPONSE_BODY", columnDefinition = "LONGTEXT")
	    private String responseBody;

	    @Lob
	    @Column(name = "EXCEPTION_MESSAGE", columnDefinition = "LONGTEXT")
	    private String exceptionMessage;

	    @Column(name = "STATUS_CODE")
	    private int statusCode;

	    @Column(name = "TIME_STAMP")
	    private LocalDateTime timestamp;

	    @Column(name = "METHOD_SIGNATURE", length = 500)
	    private String methodSignature;

	    @Column(name = "CLIENT_IP", length = 45)
	    private String clientIp;

	    @Column(name = "USERNAME", length = 255)
	    private String username;

	    @Column(name = "EXECUTION_TIMEMS")
	    private Long executionTimeMs;

	    @Lob
	    @Column(name = "USER_AGENT", columnDefinition = "LONGTEXT")
	    private String userAgent;
	}
