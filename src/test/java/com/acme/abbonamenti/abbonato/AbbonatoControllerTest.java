package com.acme.abbonamenti.abbonato;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.acme.abbonamenti.abbonamenti.Abbonamento;
import com.acme.abbonamenti.abbonamenti.AbbonamentoRepository;
import com.acme.abbonamenti.abbonati.Abbonato;
import com.acme.abbonamenti.abbonati.AbbonatoRepository;
import com.acme.abbonamenti.general.TestControllerBase;


public class AbbonatoControllerTest extends TestControllerBase {
	@Autowired
	AbbonatoRepository abbonatoRepository;
	
	@Autowired
	AbbonamentoRepository abbonamentoRepository;
	
	@Value("${test.entry.point}/abbonato")
	private String URL;
	
	@Test
	@DisplayName("cerco l'elenco degli abbonati")
	public void testGetAll() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Rossi","Mario","1234567890123456", l);
		abbonatoRepository.save(a);
		
		
		ResponseEntity<String> r = getRestTemplate().getForEntity(URL, String.class);
		assertThat( r.getStatusCode() ).isEqualTo(  HttpStatus.OK );
		
	}

}
