package com.panambystudio.helpdesk.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.panambystudio.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.panambystudio.helpdesk.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request){
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value(), "Object Not Found", ex.getMessage(), request.getRequestURI()));
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> objectNotFoundException(DataIntegrityViolationException ex, HttpServletRequest request){
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Violação de dados", ex.getMessage(), request.getRequestURI()));
	}
}
