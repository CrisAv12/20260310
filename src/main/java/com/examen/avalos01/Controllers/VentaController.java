package com.examen.avalos01.Controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.examen.avalos01.Exceptions.VentaNoEncontradaException;
import com.examen.avalos01.Exceptions.ProductoNoEncontradoException;
import com.examen.avalos01.Exceptions.VentaEncontrada;
import com.examen.avalos01.Exceptions.ClienteNoEncontradoException;
import com.examen.avalos01.Exceptions.EmpleadoNoEncontradoException;

import com.examen.avalos01.Models.Empleado;
import com.examen.avalos01.Models.Cliente;
import com.examen.avalos01.Models.Producto;
import com.examen.avalos01.Models.Venta;

import com.examen.avalos01.Controllers.ProductoController;
import com.examen.avalos01.Controllers.ClienteController;
import com.examen.avalos01.Controllers.EmpleadoController;

import tools.jackson.databind.ObjectMapper;

@Controller
public class VentaController {
	
	@Autowired
	private ApplicationContext contexto;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static Map<String, Venta> ventas = new HashMap<>();
	static {
		Venta v1 = new Venta(1001,1,20304022,303,6);
		Venta v2 = new Venta(1002,3,10203011,202,30);
		Venta v3 = new Venta(1003,2,30405033,101,2);
		ventas.put("1001", v1);
		ventas.put("1002", v2);
		ventas.put("1003", v3);
	}
	
	// /venta (GET) —> Que devuelva la lista de ventas en JSON.
	// http://localhost:9092/venta
	// respuesta: 200 Ok
	@GetMapping("/venta")
	public ResponseEntity<Object> getVentas(){
		return new ResponseEntity<>(ventas.values(), HttpStatus.OK);
	}
	
	
	// /venta (POST) —> Que agregue un nuevo registro de venta enviado en JSON.
	// http://localhost:9092/venta
	/* en el Body
	 * 	{
			"id": 1004,
			"emp": 3,
			"clien": 30405033,
			"prod": 202,
			"cantidad": 5.5
		}
	 * */
	// Respuesta: 201 creado
	@PostMapping("/venta")
	public ResponseEntity<Object> nuevaVenta(@RequestBody Venta vent){
		
		/*
	     * Para probar que salta el error de venta colocar lo siguiente:
	     *
	     *http://localhost:9092/venta/1003
		/* venta 1003 YA EXISTE, lo demas también
		 * 	{
				"id": 1003,
				"emp": 3,
				"clien": 10203011,
				"prod": 202,
				"cantidad": 55
			}
	     *
	     *Respuesta de excepcion de venta , que no permite crear venta con UN CÓDIGO EXISTENTE
	     * */
		String codigoVentaExistente = String.valueOf(vent.getId());
		
		if (ventas.containsKey(codigoVentaExistente)) {
			System.out.println("entra a la excepcion");
			throw new VentaEncontrada();
		}
		
		
		/*
	     * Para probar que salta el error de producto colocar lo siguiente:
	     *
	     *http://localhost:9092/venta/1003
		/* venta 1003 existe, pero el producto 332332 NO EXISTE
		 * 	{
				"id": 1003,
				"emp": 3,
				"clien": 10203011,
				"prod": 332332,
				"cantidad": 55
			}
	     *
	     *Respuesta de excepcion del producto
	     * */
		//Venta ventaExistente = ventas.get(id);
		String codigoProducto = String.valueOf(vent.getProd());
		
	    if (!ProductoController.productos.containsKey(codigoProducto)) {
	    	System.out.println("entra a producto");
	        throw new ProductoNoEncontradoException();
	    }
	    
	    /*
	     * Para probar que salta el error de cliente colocar lo siguiente:
	     *
	     *http://localhost:9092/venta/1003
		/* venta 1003 existe, pero el cliente 2222222 NO EXISTE
		 * 	{
				"id": 1003,
				"emp": 3,
				"clien": 2222222,
				"prod": 202,
				"cantidad": 55
			}
	     *
	     *Respuesta de excepcion del cliente
	     * */
	    
	    String codigoCliente = String.valueOf(vent.getClien());
		
	    if (!ClienteController.clientes.containsKey(codigoCliente)) {
	    	System.out.println("entra a cliente");
	        throw new ClienteNoEncontradoException();
	    }
	    
	    /*
	     * Para probar que salta el error de empleado colocar lo siguiente:
	     *
	     *http://localhost:9092/venta/1003
		/* venta 1003 existe, pero el empleado 676 NO EXISTE
		 * 	{
				"id": 1003,
				"emp": 676,
				"clien": 10203011,
				"prod": 202,
				"cantidad": 55
			}
	     *
	     *Respuesta de excepcion del empleado
	     * */

	    String codigoEmpleado = String.valueOf(vent.getEmp());
	    		
	    if (!EmpleadoController.empleados.containsKey(codigoEmpleado)) {
	    	System.out.println("entra a empleado");
	        throw new EmpleadoNoEncontradoException();
	    }
	    
		
		
		
		ventas.put(vent.getId() +"", vent);
		URI ubicacionRecurso = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(vent.getId())
				.toUri();
		return ResponseEntity.created(ubicacionRecurso).body(vent);
	}
	
	// /venta/{id} (PUT) —> Que edite un registro de venta mediante su ID, registro pasado en JSON.
	// http://localhost:9092/venta/1003
	/* en el Body cambia cliente (clien) y cantidad
	 * 	{
			"id": 1003,
			"emp": 3,
			"clien": 10203011,
			"prod": 202,
			"cantidad": 55
		}
	 * */
	// Respuesta: 202 Aceptado
	@PutMapping("/venta/{id}")
	public ResponseEntity<Object> editarEmpleado(@PathVariable("id") String id,@RequestBody Venta vent){
		
		
		/*
	     * Para probar que salta el error de venta colocar lo siguiente:
	     *
	     *http://localhost:9092/venta/1003
		/* venta 2000 NO  EXISTE, lo demas sí
		 * 	{
				"id": 2000,
				"emp": 3,
				"clien": 10203011,
				"prod": 202,
				"cantidad": 55
			}
	     *
	     *Respuesta de excepcion de venta
	     * */
		
		if (!ventas.containsKey(id)) {
			throw new VentaNoEncontradaException();
		}
		
		/*
	     * Para probar que salta el error de producto colocar lo siguiente:
	     *
	     *http://localhost:9092/venta/1003
		/* venta 1003 existe, pero el producto 332332 NO EXISTE
		 * 	{
				"id": 1003,
				"emp": 3,
				"clien": 10203011,
				"prod": 332332,
				"cantidad": 55
			}
	     *
	     *Respuesta de excepcion del producto
	     * */
		//Venta ventaExistente = ventas.get(id);
		String codigoProducto = String.valueOf(vent.getProd());
		
	    if (!ProductoController.productos.containsKey(codigoProducto)) {
	        throw new ProductoNoEncontradoException();
	    }
	    
	    /*
	     * Para probar que salta el error de cliente colocar lo siguiente:
	     *
	     *http://localhost:9092/venta/1003
		/* venta 1003 existe, pero el cliente 2222222 NO EXISTE
		 * 	{
				"id": 1003,
				"emp": 3,
				"clien": 2222222,
				"prod": 202,
				"cantidad": 55
			}
	     *
	     *Respuesta de excepcion del cliente
	     * */
	    
	    String codigoCliente = String.valueOf(vent.getClien());
		
	    if (!ClienteController.clientes.containsKey(codigoCliente)) {
	        throw new ClienteNoEncontradoException();
	    }
	    
	    /*
	     * Para probar que salta el error de empleado colocar lo siguiente:
	     *
	     *http://localhost:9092/venta/1003
		/* venta 1003 existe, pero el empleado 676 NO EXISTE
		 * 	{
				"id": 1003,
				"emp": 676,
				"clien": 10203011,
				"prod": 202,
				"cantidad": 55
			}
	     *
	     *Respuesta de excepcion del empleado
	     * */

	    String codigoEmpleado = String.valueOf(vent.getEmp());
	    		
	    if (!EmpleadoController.empleados.containsKey(codigoEmpleado)) {
	        throw new EmpleadoNoEncontradoException();
	    }
	    
		//elimina la venta porque ya lo recuperamos en vent
		ventas.remove(id);
		vent.setId(Integer.parseInt(id));
		ventas.put(id,vent);
		return new ResponseEntity<>("Se editó la venta numero: "+id, HttpStatus.ACCEPTED);
	}
	
	
	// /venta/{id} (DELETE) —> Que elimine al registro de venta de ese ID de la lista.
	// http://localhost:9092/venta/1003
	// Respuesta: 208 Ya reportado, solo con fines de mostrar la manipulación de respuesta mostrada
	
	@DeleteMapping("/venta/{id}")
	public ResponseEntity<Object> eliminarVenta(@PathVariable("id") String id){
		ventas.remove(id);
		return new ResponseEntity<>("Se eliminó la venta numero: "+id, HttpStatus.ALREADY_REPORTED);
	}
}
