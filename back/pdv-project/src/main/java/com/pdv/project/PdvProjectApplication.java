package com.pdv.project;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class PdvProjectApplication {

	@Value("${files.in}")
	private String inputDir;

	public static void main(String[] args) {
		SpringApplication.run(PdvProjectApplication.class, args);
	}

//	@Component
//	class ProcessXMLFilesRoutes extends RouteBuilder {
//
//		private final Logger LOG = LoggerFactory.getLogger(ProcessXMLFilesRoutes.class);
//
//		@Override
//		public void configure() throws Exception {
//			LOG.info("Extraction des points de vente depuis le repertoire: " + inputDir);
//
//			from("file:///{{files.in}}?antInclude={{files.pattern}}").to("file:///{{files.done}}")
//					.log("Processing file ${file:onlyname}").to("direct:processXML");
//
//			from("direct:processXML").split().tokenizeXML("pdv").streaming().bean("transform")
//					.log(LoggingLevel.INFO, "PDV", "${body}");
//
//			// from("direct:processXML").split().tokenizeXML("pdv").streaming().setHeader("city")
//			// .xpath("/pdv/ville/text()").log("City: ${header.city}");
//
//			// from("direct:processXML").split().tokenizeXML("pdv").streaming().bean("transform",
//			// "toPrice")
//			// .to("jpa:com.pdv.project.model.Ouverture").log("Inserted new City:
//			// ${body.id}");
//		}
//
//	}
}
