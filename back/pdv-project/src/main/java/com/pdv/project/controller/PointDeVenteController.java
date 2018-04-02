package com.pdv.project.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MOHAMED LAMINE SYLLA
 *
 */
@RestController
@RequestMapping("/demo")
public class PointDeVenteController {

	@Value("${author.nom.key}")
	private String name;

	//	private String test = StringUtils.EMPTY;

	@RequestMapping("/home")
	public String home() {
		return "Hello  world " + name;
	}

}
