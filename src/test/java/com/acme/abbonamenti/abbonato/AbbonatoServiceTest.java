package com.acme.abbonamenti.abbonato;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.acme.abbonamenti.abbonamenti.Abbonamento;
import com.acme.abbonamenti.abbonati.Abbonato;
import com.acme.abbonamenti.abbonati.AbbonatoDTO;
import com.acme.abbonamenti.abbonati.AbbonatoRepository;
import com.acme.abbonamenti.abbonati.AbbonatoService;
import com.acme.abbonamenti.errors.AlreadyInsertedException;
import com.acme.abbonamenti.general.TestBase;


public class AbbonatoServiceTest extends TestBase {
	@Autowired
	AbbonatoService abbonatoService;
	@Autowired
	AbbonatoRepository abbonatoRepository;

	
	@Test
	@DisplayName("Inserisco un abbonato e la trovo con il servizio ")
	public void testGetOk() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Rossi","Mario","qyerughdflitrwls", l);
		abbonatoRepository.save(a);
		
		
		Abbonato afound = abbonatoService.getAbbonato(a.getId());
		
		assertThat( afound).isNotNull();
		assertThat( afound.getCodiceFiscale()).isEqualTo(a.getCodiceFiscale());
	}
	
	@Test
	@DisplayName("Inserisco con successo un abbonato usando il servizio")
	public void testPostOk() throws AlreadyInsertedException {
		AbbonatoDTO adto = new AbbonatoDTO("Roberto","De Maria","1234567890123455");
	
		abbonatoService.inserisciAbbonato(adto);
		Abbonato a = abbonatoRepository.findByCodiceFiscale(adto.getCodiceFiscale());
		
		assertThat(a).isNotNull();
		assertThat(a.getCodiceFiscale()).isEqualTo(adto.getCodiceFiscale());
	}
	
	@Test
	@DisplayName("Provo ad inserire un abbonato dando dei valori non validi")
	public void testPostWithWrongValues( ) {
		AbbonatoDTO adto = new AbbonatoDTO("","","");
		assertThatThrownBy( () -> abbonatoService.inserisciAbbonato(adto)  )
		.isInstanceOf(ConstraintViolationException.class)
		.hasMessageContaining("dto.nome")
		.hasMessageContaining("dto.cognome")
		.hasMessageContaining("dto.codiceFiscale");
	}
	
	@Test
	@DisplayName("Provo ad inserire un abbonato già presente nel db")
	public void testPostPersonKoAlreadyPresent() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Rossi","Mario","qydgerlsorjhrhdf", l);
		abbonatoRepository.save(a);
		
		AbbonatoDTO adto = new AbbonatoDTO( a.getNome(),a.getCognome(), a.getCodiceFiscale()  );
		assertThatThrownBy( () -> abbonatoService.inserisciAbbonato(adto)  )
		.isInstanceOf(AlreadyInsertedException.class)
		.hasMessage("Abbonato già inserito");
	}
	
	@Test
	@DisplayName("Cerco un abbonato inesistente")
	public void testGetUnknowElement() {
		assertThatThrownBy( () -> abbonatoService.getAbbonato(100000l) )
		.isInstanceOf(EntityNotFoundException.class)
		.hasMessage("Abbonato non trovato");
	}
	
	@Test
	@DisplayName("Cerco di inserire un abbonato usando il servizio ed ottengo un eccezione di Tipo AlreadyInsertedException")
	public void testPostKoAlreadyPresent()  {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Rossi","Mario","qydgerlsorjhrhdf", l);
		abbonatoRepository.save(a);
		
		AbbonatoDTO adto = new AbbonatoDTO(  a.getNome(),a.getCognome()   ,a.getCodiceFiscale());
		
		assertThatThrownBy( () ->  abbonatoService.inserisciAbbonato(adto)  )
		.isInstanceOf(AlreadyInsertedException.class)
		.hasMessage("Abbonato già inserito") ;
	}	
	
	@Test
	@DisplayName("Cerco di inserire un abbonato ma i dati errati provocano un eccezione di tipo CostraintValidationException")
	public void testValidationKo() {
		 
		AbbonatoDTO adto = new AbbonatoDTO("","De Maria","123456789012");
		
		assertThatThrownBy( () -> abbonatoService.inserisciAbbonato(adto) )
		.isInstanceOf(  ConstraintViolationException.class )
		.hasMessageContaining("nome")
		.hasMessageContaining("codiceFiscale");
	}
	
	@Test
	@DisplayName("Cerco un abbonato senza trovarla")
	public void testGetKo() {
		 
		
		assertThatThrownBy( () -> abbonatoService.getAbbonato(1000l) )
		.isInstanceOf(  EntityNotFoundException.class )
		.hasMessage("Abbonato non trovato");
	}
	
}
