package com.examen.avalos01.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.examen.avalos01.Exceptions.EmpleadoNoEncontradoException;

@ControllerAdvice
public class EmpleadoExceptionController {
	@ExceptionHandler(value=EmpleadoNoEncontradoException.class)
	public ResponseEntity<Object> empExcepcion(){
		return new ResponseEntity<>("No se pudo encontrar un empleado con ese C.I.", HttpStatus.NOT_FOUND);
	}
}
