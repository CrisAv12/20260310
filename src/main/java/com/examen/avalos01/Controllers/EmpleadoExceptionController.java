package com.examen.avalos01.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.examen.avalos01.Exceptions.ClienteEncontrado;
import com.examen.avalos01.Exceptions.ClienteNoEncontradoException;
import com.examen.avalos01.Exceptions.EmpleadoEncontrado;
import com.examen.avalos01.Exceptions.EmpleadoNoEncontradoException;
import com.examen.avalos01.Exceptions.NoEsNumeroException;
import com.examen.avalos01.Exceptions.NumeroNegativo;
import com.examen.avalos01.Exceptions.ProductoNoEncontradoException;
import com.examen.avalos01.Exceptions.VentaNoEncontradaException;
import com.examen.avalos01.Exceptions.VentaEncontrada;

@ControllerAdvice
public class EmpleadoExceptionController {
	
	// se centraliza el control de las excepciones en esta clase
	
	@ExceptionHandler(value=EmpleadoNoEncontradoException.class)
	public ResponseEntity<Object> empExcepcion(){
		return new ResponseEntity<>("No se pudo encontrar un empleado con ese C.I.", HttpStatus.NOT_FOUND);
	
	}
	
	@ExceptionHandler(value=ClienteNoEncontradoException.class)
	public ResponseEntity<Object> cliExcepcion(){
		return new ResponseEntity<>("No se pudo encontrar un cliente con ese NIT", HttpStatus.NOT_FOUND);
	
	}
	
	@ExceptionHandler(value=ProductoNoEncontradoException.class)
	public ResponseEntity<Object> proExcepcion(){
		return new ResponseEntity<>("No se pudo encontrar un producto con ese código", HttpStatus.NOT_FOUND);
	
	}
	
	
	@ExceptionHandler(value=VentaNoEncontradaException.class)
	public ResponseEntity<Object> venExcepcion(){
		return new ResponseEntity<>("No se pudo registrar una venta porque la venta con ese código no existe", HttpStatus.NOT_FOUND);
	
	}
	
	
	@ExceptionHandler(value=VentaEncontrada.class)
	public ResponseEntity<Object> ventaExcepcion(){
		return new ResponseEntity<>("No se pudo registrar una venta porque YA EXISTE una venta con ese código", HttpStatus.BAD_REQUEST);
	
	}
	
	@ExceptionHandler(value=ClienteEncontrado.class)
	public ResponseEntity<Object> clienteExcepcion(){
		return new ResponseEntity<>("No se pudo registrar el cliente porque YA EXISTE un registro con ese código", HttpStatus.BAD_REQUEST);
	
	}
	
	
	@ExceptionHandler(value=NoEsNumeroException.class)
	public ResponseEntity<Object> numeroExcepcion(){
		return new ResponseEntity<>("El valor ingresado NO ES UN NÚMERO", HttpStatus.BAD_REQUEST);
	
	}
	
	@ExceptionHandler(value=EmpleadoEncontrado.class)
	public ResponseEntity<Object> empleaExcepcion(){
		return new ResponseEntity<>("No se pudo registrar el empleado porque YA EXISTE un registro con ese código", HttpStatus.BAD_REQUEST);
	
	}
	
	@ExceptionHandler(value=NumeroNegativo.class)
	public ResponseEntity<Object> negativoExcepcion(){
		return new ResponseEntity<>("El valor ingresado ES NEGATIVO ", HttpStatus.BAD_REQUEST);
	
	}
	
}
