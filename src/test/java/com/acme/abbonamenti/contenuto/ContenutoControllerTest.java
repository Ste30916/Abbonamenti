package com.acme.abbonamenti.contenuto;

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
import com.acme.abbonamenti.contenuti.Contenuto;
import com.acme.abbonamenti.contenuti.ContenutoDTO;
import com.acme.abbonamenti.contenuti.ContenutoRepository;
import com.acme.abbonamenti.general.TestControllerBase;

public class ContenutoControllerTest extends TestControllerBase {
	@Autowired
	ContenutoRepository contenutoRepository;
	
	@Value("${test.entry.point}/contenuto")
	private String URL;
	
	@Test
	@DisplayName("cerco l'elenco dei contenuti")
	public void testGetAll() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Contenuto c = new Contenuto(null,"Netflix", 18.0, l);
		contenutoRepository.save(c);
		
		
		ResponseEntity<String> r = getRestTemplate().getForEntity(URL, String.class);
		assertThat( r.getStatusCode() ).isEqualTo(  HttpStatus.OK );
		
	}

	@Test
	@DisplayName("Cerco e trovo un contenuto by nome")
	public void getByIdOk() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Contenuto c = new Contenuto(null,"Prime Video", 23.0, l);
		contenutoRepository.save(c);

		ResponseEntity<Contenuto> r = getRestTemplate().getForEntity(URL+"/"+c.getId(), Contenuto.class);
		
		assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK );
		Contenuto c1 = r.getBody();
		
		
		assertThat(  c1.getNome() ).isEqualTo(c.getNome()  );
		assertThat(  c1.getTariffa() ).isEqualTo(c.getTariffa()  );
	}
	
	
	@Test
	@DisplayName("Cerco un contenuto inesistente e ottengo status NOT_FOUND")
	public void getByIdKo() {
		ResponseEntity<String> r = getRestTemplate().getForEntity(URL+"/100000", String.class);
		assertThat(  r.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
	}
	
	@Test
	@DisplayName("Inserisco un contenuto con successo")
	public void testPostOk() {
		ContenutoDTO c = new ContenutoDTO("Disney Plus", 12.0);
		ResponseEntity<String> r = getRestTemplate().postForEntity(URL, c, String.class);
		
		assertThat(r.getStatusCode()).isEqualTo( HttpStatus.OK);
		
		Contenuto c1 = contenutoRepository.findByNome( c.getNome() );
		
		assertThat(c1).isNotNull();
	}
	
	@Test
	@DisplayName("Provo ad inserire un contenuto già esistente ottengo status BAD_REQUEST")
	public void postKoAlreadyInsertedException() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Contenuto c = new Contenuto(null,"Hulu", 10.0, l);
		contenutoRepository.save(c);
		
		ContenutoDTO dto = new ContenutoDTO("Hulu", 10.0);
		ResponseEntity<String> r = getRestTemplate().postForEntity(URL, dto, String.class);
		
		assertThat( r.getStatusCode() ).isEqualTo( HttpStatus.BAD_REQUEST );
	}
	
	@Test
	@DisplayName("Provo ad inserire un abbonato ma il nome è vuoto e ottengo status BAD_REQUEST")
	public void postKoValidationErrors() {
		ContenutoDTO dto = new ContenutoDTO("", 100.0);
		ResponseEntity<String> r = getRestTemplate().postForEntity(URL, dto, String.class);
		
		assertThat(r.getStatusCode()).isEqualTo( HttpStatus.BAD_REQUEST );
		
		
	}
	
	
	
}

