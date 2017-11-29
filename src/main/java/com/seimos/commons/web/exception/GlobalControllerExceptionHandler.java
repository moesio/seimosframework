package com.seimos.commons.web.exception;

import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author moesio @ gmail.com
 * @date Dec 8, 2014 10:40:10 PM
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

	//		@ExceptionHandler(value = { RuntimeException.class })
	//		protected ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest request) {
	//			return handleExceptionInternal(exception, "specific body", new HttpHeaders(), HttpStatus.CONFLICT, request);
	//		}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorMessage handleException(HibernateException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getLocalizedMessage());
		return errorMessage;
	}

}
