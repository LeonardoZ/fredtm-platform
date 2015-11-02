package com.fredtm.api.exception;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ExceptionHandlingController {

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation") // 409
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void conflict() {
	}

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Invalid data passed to persist") // 409
	@ExceptionHandler({ SQLException.class, DataAccessException.class })
	public void databaseError() {
	}

	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid auth credentials") // 401
	@ExceptionHandler({ UnauthorizedExcpetion.class })
	public void authError() {
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid values passed to persist") // 400
	@ExceptionHandler({ IllegalArgumentException.class })
	public void wrongValuesPassedToPersist() {
	
	}
	
}
