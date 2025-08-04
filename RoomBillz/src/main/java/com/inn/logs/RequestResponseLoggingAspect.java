package com.inn.logs;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inn.repository.IApiLogRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Aspect
@Component
public class RequestResponseLoggingAspect {

	@Autowired
	private IApiLogRepository apiLogRepository;

	@Around("@annotation(com.inn.logs.LogRequestResponse)")
	public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		ApiLog log = new ApiLog();
		log.setUrl(request.getRequestURI());
		log.setHttpMethod(request.getMethod());
		log.setTimestamp(LocalDateTime.now());
		log.setMethodSignature(joinPoint.getSignature().toShortString());
		log.setClientIp(request.getRemoteAddr());
		log.setUserAgent(request.getHeader("User-Agent"));
		log.setUsername(getLoggedInUsername());
		log.setRequestBody(getRequestBody(joinPoint));

		long startTime = System.currentTimeMillis();
		try {
			Object response = joinPoint.proceed();

			Object responseObject = (response instanceof ResponseEntity) ? ((ResponseEntity<?>) response).getBody()
					: response;

			String responseBody = getResponseBody(responseObject);
			log.setResponseBody(responseBody);
			log.setStatusCode(HttpServletResponse.SC_OK);

			return response;
		} catch (Exception ex) {
			log.setExceptionMessage(ex.getMessage());
			log.setResponseBody(null);
			log.setStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw ex;
		} finally {
			long duration = System.currentTimeMillis() - startTime;
			log.setExecutionTimeMs(duration);
			apiLogRepository.save(log);
		}
	}

	private String getRequestBody(ProceedingJoinPoint joinPoint) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		for (Object arg : joinPoint.getArgs()) {
			try {
				if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse
						|| arg instanceof org.springframework.validation.BindingResult) {
					continue;
				}
				return mapper.writeValueAsString(arg);
			} catch (Exception e) {
				continue;
			}
		}
		return "No serializable request body";
	}

	private String getResponseBody(Object response) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		try {
			return mapper.writeValueAsString(response);
		} catch (Exception e) {
			return "Unable to parse response: " + e.getMessage();
		}
	}

	private String getLoggedInUsername() {
		try {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         if (auth != null && auth.isAuthenticated()) {
//             return auth.getName();
//         }
		} catch (Exception e) {

		}
		return "anonymous";
	}
}
