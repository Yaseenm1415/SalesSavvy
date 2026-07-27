package com.app.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
		
		return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> handleAuthentication(AuthenticationException e) {
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error" , e.getMessage()));
	}
	
	@ExceptionHandler(AccesDeniedException.class)
	public ResponseEntity<?>  handleAccessDenied( AccesDeniedException e) {
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error" , e.getMessage()));
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?>  handleProductNotFound( NotFoundException e) {
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error" , e.getMessage()));
	}
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<?>  handleAlreadyExists( AlreadyExistsException e) {
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error" , e.getMessage()));
	}
	
	@ExceptionHandler(FailedException.class)
	public ResponseEntity<?>  handleFailed( FailedException e) {
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error" , e.getMessage()));
	}
	
	@ExceptionHandler(InvalidQuantityException.class)
	public ResponseEntity<?>  handleInvalidQuantity(InvalidQuantityException e) {
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error" , e.getMessage()));
	}
	
	@ExceptionHandler(OutOfStockException.class)
	public ResponseEntity<?>  handleOutOfStock(OutOfStockException e) {
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error" , e.getMessage()));
	}
}
