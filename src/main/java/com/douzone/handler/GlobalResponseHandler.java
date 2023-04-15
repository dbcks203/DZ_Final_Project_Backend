package com.douzone.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.NoSuchElementException;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.douzone.entity.ExceptionResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalResponseHandler {
	
	
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String CYAN = "\u001B[36m";
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ExceptionResponseVO> handleNullPointerException(NullPointerException e) {
		return buildExceptionResponseVO(HttpStatus.INTERNAL_SERVER_ERROR, "NullPointerException occurred.", e);
	}
// handle sql exception
	@ExceptionHandler({ DeadlockLoserDataAccessException.class, DuplicateKeyException.class,
			EmptyResultDataAccessException.class, IncorrectResultSizeDataAccessException.class,
			InvalidDataAccessApiUsageException.class, InvalidDataAccessResourceUsageException.class,
			OptimisticLockingFailureException.class, PessimisticLockingFailureException.class,
			PermissionDeniedDataAccessException.class, UncategorizedDataAccessException.class })
	public ResponseEntity<ExceptionResponseVO> handleFrequentDataAccessExceptions(Exception e) {
		return buildExceptionResponseVO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ExceptionResponseVO> handleOtherDataAccessExceptions(DataAccessException e) {
		return buildExceptionResponseVO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ExceptionResponseVO> handleSQLException(SQLException e) {
		return buildExceptionResponseVO(HttpStatus.INTERNAL_SERVER_ERROR, "SQLException occurred.", e);
	}
	// handle io exception
	@ExceptionHandler({ FileNotFoundException.class, MalformedURLException.class })
	public ResponseEntity<ExceptionResponseVO> handleFrequentIOExceptions(Exception e) {
		return buildExceptionResponseVO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ExceptionResponseVO> handleIOException(IOException e) {
		return buildExceptionResponseVO(HttpStatus.INTERNAL_SERVER_ERROR, "IOException occurred.", e);
	}

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<ExceptionResponseVO> handleMissingPathVariableException(MissingPathVariableException e) {
		return buildExceptionResponseVO(HttpStatus.BAD_REQUEST, "Missing path variable.", e);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponseVO> handleIllegalArgumentException(IllegalArgumentException e) {
		return buildExceptionResponseVO(HttpStatus.BAD_REQUEST, "IllegalArgument", e);
	}

	@ExceptionHandler(TypeMismatchException.class)
	public ResponseEntity<ExceptionResponseVO> handleTypeMismatchException(TypeMismatchException e) {
		return buildExceptionResponseVO(HttpStatus.BAD_REQUEST, "Type mismatch.", e);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ExceptionResponseVO> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e) {
		return buildExceptionResponseVO(HttpStatus.METHOD_NOT_ALLOWED, "HTTP method not supported.", e);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ExceptionResponseVO> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException e) {
		return buildExceptionResponseVO(HttpStatus.BAD_REQUEST, "Missing request parameter.", e);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ExceptionResponseVO> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException e) {
		return buildExceptionResponseVO(HttpStatus.BAD_REQUEST, "Invalid request format", e);
	}
	
	@ExceptionHandler(NotReadablePropertyException.class)
	public ResponseEntity<ExceptionResponseVO> handleNotReadableProperty(NotReadablePropertyException e) {
	    return buildExceptionResponseVO(HttpStatus.BAD_REQUEST, "Invalid request", e);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponseVO> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
		return buildExceptionResponseVO(HttpStatus.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage(), e);
	}
	

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ExceptionResponseVO> handleValidationException(ValidationException e) {
	
	    String defaultMessage =  e.getErrors().getFieldError().getDefaultMessage();
	    return buildExceptionResponseVO(HttpStatus.BAD_REQUEST, defaultMessage, e);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ExceptionResponseVO> handleNoSuchElementException(NoSuchElementException e) {
		return buildExceptionResponseVO(HttpStatus.NOT_FOUND, e.getMessage(), e);
	}

	// 나머지 예외들을 처리하기 위한 메서드
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponseVO> handleAllExceptions(Exception e) {
		return buildExceptionResponseVO(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", e);
	}

	
	private ResponseEntity<ExceptionResponseVO> buildExceptionResponseVO(HttpStatus status, String message,
			Exception e) {
		ExceptionResponseVO errorResponse = new ExceptionResponseVO(status.value(), LocalDateTime.now(), false,
				message);
		
		int statusCode = status.value();
		String logMessage = "Server Error: \n"+color(RED, "Status - {}")+color(GREEN, "\nMessage - {}")+color(CYAN,"\nDetails - {}");
		
		if (statusCode >= 500) {
			log.error(logMessage, statusCode, message, e.getMessage());
			log.error("StackTrace:{}",ExceptionUtils.getStackTrace(e));
		}
		else if (statusCode >= 400) {
			log.warn(logMessage, statusCode, message, e.getMessage());
		}  
		
		return new ResponseEntity<>(errorResponse, status);
	}

	
	// response 전 필요한 작업을 수행하는 메소드
	@ResponseBody
	public Map<String, Object> handleResponse(Map<String, Object> result, HttpStatus status) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		result.put("time_stamp", formattedDateTime);
		result.put("status_code", status.value());
		result.put("status", true);
		return result;
	}
	
	
	private static String color(String colorCode, String text) {
        return colorCode + text + RESET;
    }

}
