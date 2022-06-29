package com.acme.abbonamenti.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import lombok.Getter;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Getter
public class TestControllerBase extends TestBase {
	@Autowired
	private TestRestTemplate restTemplate;

}
