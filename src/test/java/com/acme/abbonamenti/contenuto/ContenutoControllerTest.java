package com.acme.abbonamenti.contenuto;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.acme.abbonamenti.abbonamenti.Abbonamento;
import com.acme.abbonamenti.abbonamenti.AbbonamentoRepository;
import com.acme.abbonamenti.contenuti.Contenuto;
import com.acme.abbonamenti.contenuti.ContenutoRepository;
import com.acme.abbonamenti.general.TestControllerBase;

public class ContenutoControllerTest extends TestControllerBase {
	@Autowired
	ContenutoRepository contenutoRepository;
	
	@Value("${test.entry.point}/abbonato")
	private String URL;
	
	@Test
	@DisplayName("cerco l'elenco dei contenuti")
	public void testGetAll() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Contenuto c = new Contenuto(null,"Rossi","Mario","vbfhsjelonrawldc", l);
		contenutoRepository.save(c);
		
		
		ResponseEntity<String> r = getRestTemplate().getForEntity(URL, String.class);
		assertThat( r.getStatusCode() ).isEqualTo(  HttpStatus.OK );
		
	}

	@Test
	@DisplayName("Cerco e trovo un abbonato by id")
	public void getByIdOk() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Rossi","Mario","1234567890123456", l);
		abbonatoRepository.save(a);
		
		ResponseEntity<Abbonato> r = getRestTemplate().getForEntity(URL+"/"+a.getId(), Abbonato.class);
		
		assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK );
		Abbonato a1 = r.getBody();
		
		assertThat(  a1.getCodiceFiscale() ).isEqualTo( a.getCodiceFiscale()  );
		assertThat(  a1.getId() ).isEqualTo( a.getId()  );
		assertThat(  a1.getNome() ).isEqualTo(a.getNome()  );
		assertThat(  a1.getCognome() ).isEqualTo(a.getCognome()  );
	}
	
	
	@Test
	@DisplayName("Cerco un abbonato inesistente e ottengo status NOT_FOUND")
	public void getByIdKo() {
		ResponseEntity<String> r = getRestTemplate().getForEntity(URL+"/100000", String.class);
		assertThat(  r.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
	}
	
	@Test
	@DisplayName("Inserisco un abbonato con successo")
	public void testPostOk() {
		AbbonatoDTO a = new AbbonatoDTO("Mario","Rossi","qkfgcbdlormusncx");
		ResponseEntity<String> r = getRestTemplate().postForEntity(URL, a, String.class);
		
		assertThat(r.getStatusCode()).isEqualTo( HttpStatus.OK);
		
		Abbonato ab = abbonatoRepository.findByCodiceFiscale( a.getCodiceFiscale() );
		
		assertThat(ab).isNotNull();
	}
	
	@Test
	@DisplayName("Provo ad inserire un abbonato già esistente ottengo status BAD_REQUEST")
	public void postKoAlreadyInsertedException() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Rossi","Mario","qyerughdflitrwls", l);
		abbonatoRepository.save(a);
		
		AbbonatoDTO dto = new AbbonatoDTO("Rossi","Mario","qyerughdflitrwls");
		ResponseEntity<String> r = getRestTemplate().postForEntity(URL, dto, String.class);
		
		assertThat( r.getStatusCode() ).isEqualTo( HttpStatus.BAD_REQUEST );
	}
	
	@Test
	@DisplayName("Provo ad inserire un abbonato ma il nome è vuoto e ottengo status BAD_REQUEST")
	public void postKoValidationErrors() {
		AbbonatoDTO dto = new AbbonatoDTO(null,"Aurelio","12dfghjklzxcvbuu");
		ResponseEntity<String> r = getRestTemplate().postForEntity(URL, dto, String.class);
		
		assertThat(r.getStatusCode()).isEqualTo( HttpStatus.BAD_REQUEST );
		
		
	}
	
	
	
}

