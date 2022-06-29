package com.acme.abbonamenti.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.acme.abbonamenti.abbonamenti.Abbonamento;
import com.acme.abbonamenti.abbonamenti.AbbonamentoDTO;
import com.acme.abbonamenti.abbonati.Abbonato;
import com.acme.abbonamenti.abbonati.AbbonatoDTO;
import com.acme.abbonamenti.contenuti.Contenuto;
import com.acme.abbonamenti.contenuti.ContenutoDTO;


@Component
@Profile("!test")
public class RestTemplateRunner implements ApplicationRunner {

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		AbbonatoDTO adto = new AbbonatoDTO("Mario", "Rossi", "1234567890123456");
		ContenutoDTO cdto = new ContenutoDTO("Netflix", 18.0);	
		AbbonamentoDTO bdto = new AbbonamentoDTO("1234567890123456", "Netflix", "14/08/2022", 30 );
		
		// POST
		ResponseEntity<String> respInsert = restTemplate.postForEntity("http://localhost:8080/abbonato", adto, String.class);
		String s = respInsert.getBody();
		System.out.println(s);
		
		respInsert = restTemplate.postForEntity("http://localhost:8080/contenuto", cdto, String.class);
		s = respInsert.getBody();
		System.out.println(s);
		
		respInsert = restTemplate.postForEntity("http://localhost:8080/abbonamento", bdto, String.class);
		s = respInsert.getBody();
		System.out.println(s);
		// POST
		
		// GET ID
		ResponseEntity<Abbonato>  resp0 =  restTemplate.getForEntity("http://localhost:8080/abbonato/1", Abbonato.class);
		Abbonato a = resp0.getBody();
		System.out.println(a);
		
		ResponseEntity<Contenuto>  resp1 =  restTemplate.getForEntity("http://localhost:8080/contenuto/1", Contenuto.class);
		Contenuto c = resp1.getBody();
		System.out.println(c);
		
		ResponseEntity<Abbonamento>  resp2 =  restTemplate.getForEntity("http://localhost:8080/abbonamento/1", Abbonamento.class);
		Abbonamento b = resp2.getBody();
		System.out.println(b);
		// GET ID
		
		
		
		
		
	}

}
