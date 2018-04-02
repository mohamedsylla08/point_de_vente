package com.pdv.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pdv.project.service.interfaces.IPdvBusinessService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PdvProjectApplicationTests {

	@Mock
	private IPdvBusinessService pdvBusinessService;

	private Logger log = LoggerFactory.getLogger(PdvProjectApplicationTests.class);

	@Test
	public void testGetFileXml() throws Exception {
		log.debug("Test à mettre en place");

	}

}
