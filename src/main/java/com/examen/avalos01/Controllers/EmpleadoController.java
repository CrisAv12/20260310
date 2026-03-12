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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.examen.avalos01.Exceptions.EmpleadoNoEncontradoException;
import com.examen.avalos01.Models.Empleado;

import tools.jackson.databind.ObjectMapper;

@Controller
public class EmpleadoController {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static Map<String, Empleado> empleados = new HashMap<>();
	static {
		Empleado e1 = new Empleado(1,"Luis Alberto","Morales Loza",70100001,"lmorales@mail.com");
		Empleado e2 = new Empleado(2,"Juana D.","Martinez",70200002,"jmartinez@mail.com");
		Empleado e3 = new Empleado(3,"Pedro","Olave",70300003,"polave@mail.com");
		empleados.put("1", e1);
		empleados.put("2", e2);
		empleados.put("3", e3);
	}
	
	// /empleados (GET) —> Que devuelva la lista de empleados en JSON.
	// http://localhost:9092/empleados
	// respuesta: 200 Ok
	@GetMapping("/empleados")
	public ResponseEntity<Object> getEmpleados(){
		return new ResponseEntity<>(empleados.values(), HttpStatus.OK);
	}
	
	
	// /empleados (POST) —> Que registre un nuevo empleado enviado en JSON.
	// http://localhost:9092/empleados
	/* en el Body
	 * 	{
			"ci": 4,
			"nombre": "Daniel",
			"apellido": "Ruiz ",
			"telefono": 70400004,
			"email": "druiz@mail.com"
		}
	 * */
	// Respuesta: 201 creado
	@PostMapping("/empleados")
	public ResponseEntity<Object> nuevoEmpleado(@RequestBody Empleado emple){
		empleados.put(emple.getCi() +"", emple);
		URI ubicacionRecurso = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{ci}")
				.buildAndExpand(emple.getCi())
				.toUri();
		return ResponseEntity.created(ubicacionRecurso).body(emple);
	}
	
	// /empleados/{ci} (PUT) —> Que edite un empleado mediante su CI, registro pasado en JSON.
	// http://localhost:9092/empleados/3
	/* en el Body cambia nombre y telefono
	 * 	{
			"ci": 3,
			"nombre": "Pablo",
			"apellido": "Olave",
			"telefono": 70123456,
			"email": "polave@mail.com"
		}
	 * */
	// Respuesta: 202 Aceptado
	@PutMapping("/empleados/{ci}")
	public ResponseEntity<Object> editarEmpleado(@PathVariable("ci") String ci,@RequestBody Empleado emple){
		//provocando la excepción
		if (!empleados.containsKey(ci)) {
			throw new EmpleadoNoEncontradoException();
		}
		//elimina el empleado porque ya lo recuperamos en emple
		empleados.remove(ci);
		emple.setCi(Integer.parseInt(ci));
		empleados.put(ci, emple);
		return new ResponseEntity<>("Se editó el empleado "+ci, HttpStatus.ACCEPTED);
	}
	
	
	// /empleados/{ci} (DELETE) —> Que elimine al empleado de ese CI de la lista.
	// http://localhost:9092/empleados/3
	// Respuesta: 208 Ya reportado, solo con fines de mostrar la manipulación de respuesta mostrada
	
	@DeleteMapping("/empleados/{ci}")
	public ResponseEntity<Object> eliminarEmpleado(@PathVariable("ci") String ci){
		empleados.remove(ci);
		return new ResponseEntity<>("Se eliminó el empleado "+ci, HttpStatus.ALREADY_REPORTED);
	}
	
}
