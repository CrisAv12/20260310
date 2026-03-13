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


import com.examen.avalos01.Exceptions.ProductoNoEncontradoException;
import com.examen.avalos01.Models.Producto;

import tools.jackson.databind.ObjectMapper;

@Controller
public class ProductoController {
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public static void setProductos(Map<String, Producto> productos) {
		ProductoController.productos = productos;
	}


	public static Map<String, Producto> productos = new HashMap<>();
	static {
		Producto p1 = new Producto(101,"Arocarbol",60.50);
		Producto p2 = new Producto(202,"Ibuprofeno 500 mg",2.75);
		Producto p3 = new Producto(303,"Suero de la vida",6.00);
		productos.put("101", p1);
		productos.put("202", p2);
		productos.put("303", p3);
	}
	
	// /producto (GET) —> Que devuelva la lista de productos en JSON.
	// http://localhost:9092/producto
	// respuesta: 200 Ok
	@GetMapping("/producto")
	public ResponseEntity<Object> getProductos(){
		return new ResponseEntity<>(productos.values(), HttpStatus.OK);
	}
	
	
	// /producto (POST) —> Que registre un nuevo producto enviado en JSON.
	// http://localhost:9092/producto
	/* en el Body
	 * 	{
			"codigo": 404,
			"nombre": "Desodorante Nivea",
			"precio": 45.20
		}
	 * */
	// Respuesta: 201 creado
	@PostMapping("/producto")
	public ResponseEntity<Object> nuevoProducto(@RequestBody Producto pro){
		productos.put(pro.getCodigo() +"", pro);
		URI ubicacionRecurso = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{codigo}")
				.buildAndExpand(pro.getCodigo())
				.toUri();
		return ResponseEntity.created(ubicacionRecurso).body(pro);
	}
	
	// /producto/{codigo} (PUT) —> Que edite un producto mediante su código, registro pasado en JSON.
	// http://localhost:9092/producto/303
	/* en el Body cambia nombre y precio
	 * 	{
			"codigo": 303,
			"nombre": "Sales de rehidratacion oral",
			"precio": 6.15
		}
	 * */
	// Respuesta: 202 Aceptado
	@PutMapping("/producto/{codigo}")
	public ResponseEntity<Object> editarProducto(@PathVariable("codigo") String codigo,@RequestBody Producto pro){
		if (!productos.containsKey(codigo)) {
			throw new ProductoNoEncontradoException();
		}
		//elimina el producto porque ya lo recuperamos en pro
		productos.remove(codigo);
		pro.setCodigo(Integer.parseInt(codigo));
		productos.put(codigo,pro);
		return new ResponseEntity<>("Se editó el producto con codigo "+codigo, HttpStatus.ACCEPTED);
	}
	
	
	// /producto/{codigo} (DELETE) —> Que elimine al producto de ese código de la lista.
	// http://localhost:9092/producto/303
	// Respuesta: 208 Ya reportado, solo con fines de mostrar la manipulación de respuesta mostrada
	
	@DeleteMapping("/producto/{codigo}")
	public ResponseEntity<Object> eliminarProducto(@PathVariable("codigo") String codigo){
		productos.remove(codigo);
		return new ResponseEntity<>("Se eliminó el producto con el codigo "+ codigo, HttpStatus.ALREADY_REPORTED);
	}
}
