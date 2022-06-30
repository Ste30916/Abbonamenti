package com.acme.abbonamenti.abbonamenti;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.acme.abbonamenti.abbonati.Abbonato;
import com.acme.abbonamenti.abbonati.AbbonatoDTO;
import com.acme.abbonamenti.abbonati.AbbonatoRepository;
import com.acme.abbonamenti.contenuti.Contenuto;
import com.acme.abbonamenti.contenuti.ContenutoRepository;
import com.acme.abbonamenti.general.TestControllerBase;

public class AbbonamentoControllerTest extends TestControllerBase {
	@Autowired
	AbbonatoRepository abbonatoRepository;
	
	@Autowired
	ContenutoRepository contenutoRepository;
	
	@Autowired
	AbbonamentoRepository abbonamentoRepository;
	
	@Value("${test.entry.point}/abbonamento")
	private String URL;
	
	@Test
	@DisplayName("cerco l'elenco degli abbonamenti")
	public void testGetAll() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Rossi","Mario","qyerughdflitrwls", l);
		abbonatoRepository.save(a);
		Contenuto c = new Contenuto(null, "Sky", 20.5, l);
		contenutoRepository.save(c);
		Abbonamento b = new Abbonamento(null, "02/02/2020", 5, a, c);
		abbonamentoRepository.save(b);
		
		
		ResponseEntity<String> r = getRestTemplate().getForEntity(URL, String.class);
		assertThat( r.getStatusCode() ).isEqualTo(  HttpStatus.OK );
	}
	
	@Test
	@DisplayName("Cerco e trovo un abbonamento by id")
	public void getByIdOk() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Rossi","Mario","qyerughdflitrwls", l);
		abbonatoRepository.save(a);
		Contenuto c = new Contenuto(null, "Sky", 20.5, l);
		contenutoRepository.save(c);
		Abbonamento b = new Abbonamento(null, "02/02/2020", 5, a, c);
		abbonamentoRepository.save(b);
		
		ResponseEntity<Abbonamento> r = getRestTemplate().getForEntity(URL+"/"+b.getId(), Abbonamento.class);
		
		assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK );
		Abbonamento b1 = r.getBody();
		
		assertThat(  b1.getAbbonato().getCodiceFiscale() ).isEqualTo( b.getAbbonato().getCodiceFiscale()  );
		assertThat(  b1.getId() ).isEqualTo( b.getId()  );
		assertThat(  b1.getDataIscrizione()).isEqualTo(b.getDataIscrizione());
		assertThat(  b1.getDurata() ).isEqualTo(b.getDurata()  );
		assertThat(  b1.getContenuto().getNome() ).isEqualTo(b.getContenuto().getNome()  );
	}
	
	@Test
	@DisplayName("Cerco un abbonamento inesistente e ottengo status NOT_FOUND")
	public void getByIdKo() {
		ResponseEntity<String> r = getRestTemplate().getForEntity(URL+"/100000", String.class);
		assertThat(  r.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
	}
	
	@Test
	@DisplayName("Inserisco un abbonamento con successo")
	public void testPostOk() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Rossi","Mario","vhfjduklosyrodfn", l);
		abbonatoRepository.save(a);
		Contenuto c = new Contenuto(null, "Dazn", 20.5, l);
		contenutoRepository.save(c);
		AbbonamentoDTO bdto = new AbbonamentoDTO("vhfjduklosyrodfn", "Dazn", "07/11/2021", 4);
		ResponseEntity<String> r = getRestTemplate().postForEntity(URL, bdto, String.class);
		
		assertThat(r.getStatusCode()).isEqualTo( HttpStatus.OK);
		
		Abbonamento b = abbonamentoRepository.findByAbbonato_idAndContenuto_idAndDataIscrizione(a.getId(), c.getId(), bdto.getDataIscrizione());
		
		assertThat(b).isNotNull();
	}
	
	@Test
	@DisplayName("Provo ad inserire un abbonamento già esistente ottengo status BAD_REQUEST")
	public void postKoAlreadyInsertedException() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Rossi","Mario","vhfjduklosypodfn", l);
		abbonatoRepository.save(a);
		Contenuto c = new Contenuto(null, "Spotify", 25.5, l);
		contenutoRepository.save(c);
		Abbonamento b = new Abbonamento(null, "03/02/2020", 5, a, c);
		abbonamentoRepository.save(b);
		
		AbbonamentoDTO bdto = new AbbonamentoDTO("vhfjduklosypodfn", "Spotify", "03/02/2020", 5);
		ResponseEntity<String> r = getRestTemplate().postForEntity(URL, bdto, String.class);
		
		assertThat( r.getStatusCode() ).isEqualTo( HttpStatus.BAD_REQUEST );
	}
	
	@Test
	@DisplayName("Provo ad inserire un abbonamento ma il nome è vuoto e ottengo status BAD_REQUEST")
	public void postKoValidationErrors() {
		AbbonamentoDTO bdto = new AbbonamentoDTO("", "", "", null);
		ResponseEntity<String> r = getRestTemplate().postForEntity(URL, bdto, String.class);
		
		assertThat(r.getStatusCode()).isEqualTo( HttpStatus.BAD_REQUEST );
		
		
	}
}
