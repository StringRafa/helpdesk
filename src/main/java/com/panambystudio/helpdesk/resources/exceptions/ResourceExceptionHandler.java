package com.panambystudio.helpdesk.resources.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.panambystudio.helpdesk.services.exceptions.AuthorizationException;
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
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Violação de dados", ex.getMessage(), request.getRequestURI()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex, HttpServletRequest request){
		
		ValidationError errors = new ValidationError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Validation error", "Erro na validação dos campos", request.getRequestURI());
		
		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
			errors.addErrors(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> validationErrors(ConstraintViolationException ex, HttpServletRequest request){
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Validation error", ex.getConstraintViolations().toString(), request.getRequestURI()));
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request){
		
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso negado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardError> authorization(AccessDeniedException e, HttpServletRequest request){
		
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso negado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
}
