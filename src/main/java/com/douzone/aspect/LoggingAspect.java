package com.douzone.aspect;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	
	@Autowired
	private Environment environment;
	
	@Pointcut("within(*..*Controller)")
	public void loggerPointCut() {
	}

	@Around("loggerPointCut()")
	public Object methodLogger(ProceedingJoinPoint joinPoint) throws Throwable {

		String[] activeProfiles = environment.getActiveProfiles();
	    for (String profile : activeProfiles) {
	        if ("prod".equals(profile)) {
	            return joinPoint.proceed();
	        }
	    }
		 
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		long startTime = System.currentTimeMillis();
		log.info("\nrequest: {}", setMethod(joinPoint,request));
		log.info("\nparams: {{}", getParams(request) + "}");

		// 메서드 실행
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();
		long processingTime = endTime - startTime;
		String responseParam = color(CYAN,new ObjectMapper().writeValueAsString(result));
		
		log.info("\nresponse: {}", setMethod(joinPoint,request));
		log.info("Processing time : {} ms", processingTime);
		log.info("\nparams: {{}", responseParam + "}");
		return result;
	}
	
	private static Map<String, Object> setMethod(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
		String layerName = joinPoint.getSignature().getDeclaringType().getSimpleName();
		String methodName = joinPoint.getSignature().getName();
		Map<String, Object> params = new LinkedHashMap<>();

		try {
			params.put("Controller", color(PURPLE, layerName));
			params.put("http_method", color(GREEN, request.getMethod()));
			params.put("method", color(RED, methodName));
			params.put("uri", color(YELLOW, request.getRequestURI()));

		} catch (Exception e) {
			log.error("LoggerAspect error", e);
		}
		return params;
	}
	
	private static String getParams(HttpServletRequest request) {
		StringBuilder result = new StringBuilder();
		Enumeration<String> params =  request.getParameterNames();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			String replaceParam = param.replaceAll("\\.", "-");
			String value = request.getParameter(param);
			result.append(replaceParam).append(" : ").append(value);
			if (params.hasMoreElements()) {
				result.append(",\n\t ");
			}
		}
		return color(CYAN,result.toString());
	}

	private static String color(String colorCode, String text) {
        return colorCode + text + RESET;
    }
}