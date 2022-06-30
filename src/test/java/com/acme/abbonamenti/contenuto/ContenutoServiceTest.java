package com.acme.abbonamenti.contenuto;

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
import com.acme.abbonamenti.contenuti.Contenuto;
import com.acme.abbonamenti.contenuti.ContenutoDTO;
import com.acme.abbonamenti.contenuti.ContenutoErrorMessagesEnum;
import com.acme.abbonamenti.contenuti.ContenutoRepository;
import com.acme.abbonamenti.contenuti.ContenutoService;
import com.acme.abbonamenti.errors.AlreadyInsertedException;
import com.acme.abbonamenti.general.TestBase;

public class ContenutoServiceTest extends TestBase {
	@Autowired
	ContenutoService contenutoService;
	@Autowired
	ContenutoRepository contenutoRepository;
	
	@Test
	@DisplayName("Inserisco un contenuto e lo trovo con il servizio ")
	public void testGetOk() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Contenuto c = new Contenuto(null, "Sky", 20.5, l);
		contenutoRepository.save(c);
		
		Contenuto cfound = contenutoService.find(c.getId());
		
		assertThat( cfound).isNotNull();
		assertThat( cfound.getNome()).isEqualTo(c.getNome());
	}
	
	@Test
	@DisplayName("Inserisco con successo un contenuto usando il servizio")
	public void testPostOk() throws AlreadyInsertedException {
		ContenutoDTO cdto = new ContenutoDTO("Prime Video", 18.0);
	
		contenutoService.inserisciContenuto(cdto);
		Contenuto c = contenutoRepository.findByNome(cdto.getNome());
		
		assertThat(c).isNotNull();
		assertThat(c.getNome()).isEqualTo(cdto.getNome());
	}
	
	@Test
	@DisplayName("Provo ad inserire un contenuto dando dei valori non validi")
	public void testPostWithWrongValues( ) {
		ContenutoDTO cdto = new ContenutoDTO("", null);
		assertThatThrownBy( () -> contenutoService.inserisciContenuto(cdto)  )
		.isInstanceOf(ConstraintViolationException.class)
		.hasMessageContaining("dto.nome")
		.hasMessageContaining("dto.tariffa");
	}
	
	@Test
	@DisplayName("Provo ad inserire un contenuto già presente nel db")
	public void testPostPersonKoAlreadyPresent() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Contenuto c = new Contenuto(null, "Netflix", 20.5, l);
		contenutoRepository.save(c);
		
		ContenutoDTO cdto = new ContenutoDTO( c.getNome(),c.getTariffa() );
		assertThatThrownBy( () -> contenutoService.inserisciContenuto(cdto)  )
		.isInstanceOf(AlreadyInsertedException.class)
		.hasMessage(ContenutoErrorMessagesEnum.Fields.CONTENUTO_GIA_ESISTENTE);
	}
	
	@Test
	@DisplayName("Cerco un contenuto inesistente")
	public void testGetUnknowElement() {
		assertThatThrownBy( () -> contenutoService.find(100000l) )
		.isInstanceOf(EntityNotFoundException.class)
		.hasMessage(ContenutoErrorMessagesEnum.Fields.CONTENUTO_NON_ESISTENTE);
	}
	
	@Test
	@DisplayName("Cerco di inserire un contenuto usando il servizio ed ottengo un eccezione di Tipo AlreadyInsertedException")
	public void testPostKoAlreadyPresent()  {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Contenuto c = new Contenuto(null, "Hulu", 25.5, l);
		contenutoRepository.save(c);
		
		ContenutoDTO cdto = new ContenutoDTO(  c.getNome(), c.getTariffa());
		
		assertThatThrownBy( () ->  contenutoService.inserisciContenuto(cdto)  )
		.isInstanceOf(AlreadyInsertedException.class)
		.hasMessage(ContenutoErrorMessagesEnum.Fields.CONTENUTO_GIA_ESISTENTE) ;
	}	
	
	@Test
	@DisplayName("Cerco di inserire un contenuto ma i dati errati provocano un eccezione di tipo CostraintValidationException")
	public void testValidationKo() {
		 
		ContenutoDTO cdto = new ContenutoDTO("s", -1D);
		
		assertThatThrownBy( () -> contenutoService.inserisciContenuto(cdto) )
		.isInstanceOf(  ConstraintViolationException.class )
		.hasMessageContaining("nome")
		.hasMessageContaining("tariffa");
	}
	
	@Test
	@DisplayName("Cerco un contenuto senza trovarlo")
	public void testGetKo() {	 
		
		assertThatThrownBy( () -> contenutoService.find(1000l) )
		.isInstanceOf(  EntityNotFoundException.class )
		.hasMessage(ContenutoErrorMessagesEnum.Fields.CONTENUTO_NON_ESISTENTE);
	}
	
	
	

}
