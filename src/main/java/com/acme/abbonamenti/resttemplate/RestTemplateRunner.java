package com.acme.abbonamenti.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.acme.abbonamenti.abbonamenti.Abbonamento;
import com.acme.abbonamenti.abbonamenti.AbbonamentoDTO;
import com.acme.abbonamenti.abbonamenti.AbbonamentoService;
import com.acme.abbonamenti.abbonati.Abbonato;
import com.acme.abbonamenti.abbonati.AbbonatoDTO;
import com.acme.abbonamenti.abbonati.AbbonatoService;
import com.acme.abbonamenti.contenuti.Contenuto;
import com.acme.abbonamenti.contenuti.ContenutoDTO;
import com.acme.abbonamenti.contenuti.ContenutoService;


@Component
@Profile("!test")
public class RestTemplateRunner implements ApplicationRunner {
	
	@Autowired
	AbbonatoService abbonatoService;
	@Autowired
	ContenutoService contenutoService;
	@Autowired
	AbbonamentoService abbonamentoService;
	
	@Autowired
	RestTemplate restTemplate;
	@Value("${application.token.admin}")
	String adminToken;
	@Value("${application.token.user}")
	String userToken;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		HttpHeaders adminHeader = new HttpHeaders();
		adminHeader.set("Authorization", "Bearer " + adminToken);
		HttpHeaders userHeader = new HttpHeaders();
		userHeader.set("Authorization", "Bearer " + userToken);
		
		AbbonatoDTO adto = new AbbonatoDTO("Mario", "Rossi", "1234567890123456");
		ContenutoDTO cdto = new ContenutoDTO("Netflix", 18.0);	
		AbbonamentoDTO bdto = new AbbonamentoDTO("1234567890123456", "Netflix", "14/08/2022", 30 );
		
		// POST
		HttpEntity<AbbonamentoDTO> userEntity = new HttpEntity(adto, userHeader);
		ResponseEntity<String> respInsert =  restTemplate.exchange(
				"http://localhost:8080/abbonato/user",
				HttpMethod.POST,
				userEntity, 
				String.class
				);
		String s = respInsert.getBody();
		System.out.println(s);
		
		HttpEntity<AbbonamentoDTO> headerEntity = new HttpEntity(cdto, adminHeader);
		respInsert =  restTemplate.exchange(
				"http://localhost:8080/contenuto/admin",
				HttpMethod.POST,
				headerEntity, 
				String.class
				);
		s = respInsert.getBody();
		System.out.println(s);
		
		userEntity = new HttpEntity(bdto, userHeader);
		respInsert = restTemplate.exchange(
				"http://localhost:8080/abbonamento/user", 
				HttpMethod.POST, 
				userEntity,
				String.class
				);
		s = respInsert.getBody();
		System.out.println(s);
		// POST
		
		// GET ID
		Abbonato a = abbonatoService.find(1);
		System.out.println(a);
		
		Contenuto c = contenutoService.find(1);
		System.out.println(c);
		
		Abbonamento b = abbonamentoService.find(1);
		System.out.println(b);
		// GET ID
		
		
		
		
		
	}

}
