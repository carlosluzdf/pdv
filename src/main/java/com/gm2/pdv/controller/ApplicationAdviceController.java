package com.gm2.pdv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gm2.pdv.dto.ResponseDTO;
import com.gm2.pdv.exceptions.InvalidOperationException;
import com.gm2.pdv.exceptions.NoItemException;
import com.gm2.pdv.exceptions.PasswordNotFoundException;

@RestControllerAdvice
public class ApplicationAdviceController {

	@ExceptionHandler(NoItemException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseDTO handleNoItemException( NoItemException ex ) {
		String messageError = ex.getMessage();

		return new ResponseDTO<>(messageError);
	}

	@ExceptionHandler(InvalidOperationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseDTO handleInvalidOperationException( InvalidOperationException ex ) {
		String messageError = ex.getMessage();

		return new ResponseDTO<>(messageError);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseDTO handleUsernameNotFoundException( UsernameNotFoundException ex ) {
		String messageError = ex.getMessage();

		return new ResponseDTO<>(messageError);
	}

	@ExceptionHandler(PasswordNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseDTO handlePasswordNotFoundException( PasswordNotFoundException ex ) {
		String messageError = ex.getMessage();

		return new ResponseDTO<>(messageError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseDTO handleValidationExceptions( MethodArgumentNotValidException ex ) {
		List<String> erros = new ArrayList<>();
		for( ObjectError erro : ex.getBindingResult().getAllErrors() ) {
			erros.add(erro.getDefaultMessage());
		}

		return new ResponseDTO(erros);
	}
}
