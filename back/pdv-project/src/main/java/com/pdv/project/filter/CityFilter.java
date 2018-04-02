package com.pdv.project.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**OO
 * @author MOHAMED LAMINE SYLLA
 *
 */
@Component
@ConfigurationProperties(prefix = "price.filter")
public class CityFilter {

	private List<String> cities = new ArrayList<>();

	private List<String> noms = new ArrayList<>();

	public List<String> getCities() {
		return cities;
	}

	public List<String> getNoms() {
		return noms;
	}

	/**
	 * Permet de vérifier si la ville en paramètre fait partie de la liste des
	 * villes dans le filtre.
	 * 
	 * @param city
	 *            la ville en paramètre
	 * @return la liste des villes dans le filtre
	 */
	public boolean keep(String city) {
		if (StringUtils.equals(city.toUpperCase(), "MELUN")) {
			this.getNoms().stream().map(String::toUpperCase).filter(n -> n.startsWith("m")).sorted().forEach(System.out::println);
		}
		return cities.contains(city);
	}

}
