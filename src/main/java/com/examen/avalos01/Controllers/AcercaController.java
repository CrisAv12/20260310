package com.examen.avalos01.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AcercaController {
	@RequestMapping("")
	@ResponseBody
	public String entrada() {
		return "Examen Spring Boot - Cristian Avalos";
	}
	
	@RequestMapping("/")
	@ResponseBody
	public String bienvenida() {
		return "Examen Spring Boot - Cristian Avalos";
	}
	
	@RequestMapping("/acerca") //http://localhost:9092/acerca
	@ResponseBody
	public String acerca() {
		return "Nombre: CRISTIAN JOSE AVALOS PAREDES";
	}
}
