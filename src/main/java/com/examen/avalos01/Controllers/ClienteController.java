package com.examen.avalos01.Controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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

import com.examen.avalos01.Exceptions.ClienteEncontrado;
import com.examen.avalos01.Exceptions.ClienteNoEncontradoException;
import com.examen.avalos01.Exceptions.NoEsNumeroException;
import com.examen.avalos01.Exceptions.VentaEncontrada;
import com.examen.avalos01.Models.Cliente;

import tools.jackson.databind.ObjectMapper;

@Controller
public class ClienteController {
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public static Map<String, Cliente> clientes = new HashMap<>();
	static {
		Cliente c1 = new Cliente(10203011,"Viscarra");
		Cliente c2 = new Cliente(20304022,"Galarza");
		Cliente c3 = new Cliente(30405033,"Rodriguez");
		clientes.put("10203011", c1);
		clientes.put("20304022", c2);
		clientes.put("30405033", c3);
	}
	
	// /cliente (GET) —> Que devuelva la lista de clientes en JSON.
	// http://localhost:9092/cliente
	// respuesta: 200 Ok
	@GetMapping("/cliente")
	public ResponseEntity<Object> getClientes(){
		return new ResponseEntity<>(clientes.values(), HttpStatus.OK);
	}
	
	
	// /cliente (POST) —> Que registre un nuevo cliente enviado en JSON.
	// http://localhost:9092/cliente
	/* en el Body
	 * 	{
			"nit": 40506014,
			"apellido": "Lozada S.R.L."
		}
	 * */
	// Respuesta: 201 creado
	@PostMapping("/cliente")
	public ResponseEntity<Object> nuevoCliente(@RequestBody Cliente cli){
		
		/*
	     * Para probar que salta el error de cliente colocar lo siguiente:
	     *
	     *http://localhost:9092/cliente
		/* cliente con ese NIT YA EXISTE, lo demas también
		 * 	{
				"nit": 10203011,
				"apellido": "Lopez"
			}
	     *
	     *Respuesta de excepcion de cliente , que no permite crear cliente con UN CÓDIGO EXISTENTE
	     * */
		String nitExistente = String.valueOf(cli.getNit());
		
		if (clientes.containsKey(nitExistente)) {
			throw new ClienteEncontrado();
		}
		
		//validación de que nit es valor entero positivo
		if (!nitExistente.matches("\\d+")) {
	        throw new NoEsNumeroException();
	    }
		
		clientes.put(cli.getNit() +"", cli);
		URI ubicacionRecurso = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{nit}")
				.buildAndExpand(cli.getNit())
				.toUri();
		return ResponseEntity.created(ubicacionRecurso).body(cli);
	}
	
	// /cliente/{nit} (PUT) —> Que edite un cliente mediante su NIT, registro pasado en JSON.
	// http://localhost:9092/cliente/10203011
	/* en el Body cambia apellido a sociedad
	 * 	{
			"nit": 10203011,
			"apellido": "Dominguez"
		}
	 * */
	// Respuesta: 202 Aceptado
	@PutMapping("/cliente/{nit}")
	public ResponseEntity<Object> editarCliente(@PathVariable("nit") String nit,@RequestBody Cliente cli){
		if (!clientes.containsKey(nit)) {
			throw new ClienteNoEncontradoException();
		}
		//elimina el cliente porque ya lo recuperamos en cli
		clientes.remove(nit);
		cli.setNit(Integer.parseInt(nit));
		clientes.put(nit, cli);
		return new ResponseEntity<>("Se editó el apellido del cliente con NIT: "+nit, HttpStatus.ACCEPTED);
	}
	
	
	// /cliente/{nit} (DELETE) —> Que elimine al cliente de ese NIT de la lista.
	// http://localhost:9092/cliente/30405033
	// Respuesta: 208 Ya reportado, solo con fines de mostrar la manipulación de respuesta mostrada
	
	@DeleteMapping("/cliente/{nit}")
	public ResponseEntity<Object> eliminarCliente(@PathVariable("nit") String nit){
		clientes.remove(nit);
		return new ResponseEntity<>("Se eliminó el cliente con el NIT: "+nit, HttpStatus.ALREADY_REPORTED);
	}
}
