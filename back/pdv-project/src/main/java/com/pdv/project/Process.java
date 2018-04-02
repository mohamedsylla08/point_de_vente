package com.pdv.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author MOHAMED LAMINE SYLLA
 *
 */
@Component
public class Process implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(Process.class);
	
	@Value("${files.in}")
	private String inputDir;

	@Override
	public void run(String... args) throws Exception {
		LOG.debug("Le chemin du repertoire des données: " + inputDir);
	}

}
