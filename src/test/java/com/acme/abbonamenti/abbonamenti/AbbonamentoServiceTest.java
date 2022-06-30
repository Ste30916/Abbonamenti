package com.acme.abbonamenti.abbonamenti;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.acme.abbonamenti.abbonati.Abbonato;
import com.acme.abbonamenti.abbonati.AbbonatoDTO;
import com.acme.abbonamenti.abbonati.AbbonatoErrorMessagesEnum;
import com.acme.abbonamenti.abbonati.AbbonatoRepository;
import com.acme.abbonamenti.contenuti.Contenuto;
import com.acme.abbonamenti.contenuti.ContenutoDTO;
import com.acme.abbonamenti.contenuti.ContenutoRepository;
import com.acme.abbonamenti.errors.AlreadyInsertedException;
import com.acme.abbonamenti.general.TestBase;

public class AbbonamentoServiceTest extends TestBase {
	@Autowired
	AbbonamentoService abbonamentoService;
	
	@Autowired
	AbbonamentoRepository abbonamentoRepository;
	@Autowired
	AbbonatoRepository abbonatoRepository;
	@Autowired
	ContenutoRepository contenutoRepository;
	
	@Test
	@DisplayName("Inserisco un abbonamento e lo trovo con il servizio ")
	public void testGetOk() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Rossi","Mario","qyerughdflitrwls", l);
		abbonatoRepository.save(a);
		Contenuto c = new Contenuto(null, "Sky", 20.5, l);
		contenutoRepository.save(c);
		Abbonamento b = new Abbonamento(null, "02/02/2020", 5, a, c);
		abbonamentoRepository.save(b);
		
		Abbonamento bfound = abbonamentoService.find(b.getId());
		
		assertThat( bfound).isNotNull();
		assertThat( bfound.getDataIscrizione()).isEqualTo(b.getDataIscrizione());
		assertThat( bfound.getDurata()).isEqualTo(b.getDurata());
	}
	
	@Test
	@DisplayName("Inserisco con successo un abbonamento usando il servizio")
	public void testPostOk() throws AlreadyInsertedException {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Luigi","Verdi","vhfjduklosyrodfn", l);
		abbonatoRepository.save(a);
		Contenuto c = new Contenuto(null, "Dazn", 15.0, l);
		contenutoRepository.save(c);
		
		AbbonamentoDTO bdto = new AbbonamentoDTO(
				"vhfjduklosyrodfn", "Dazn", "07/11/2021", 4);
		abbonamentoService.inserisciAbbonamento(bdto);
		Abbonamento b = abbonamentoRepository.
				findByAbbonato_idAndContenuto_idAndDataIscrizione
				(a.getId(), c.getId(), bdto.getDataIscrizione());
		
		assertThat(b).isNotNull();
		assertThat(b.getAbbonato().getId()).isEqualTo(a.getId());
		assertThat(b.getContenuto().getId()).isEqualTo(c.getId());
		assertThat(b.getDataIscrizione()).isEqualTo(bdto.getDataIscrizione());
		assertThat(b.getDurata()).isEqualTo(bdto.getDurata());
	}
	
	@Test
	@DisplayName("Provo ad inserire un abbonamento dando dei valori non validi")
	public void testPostWithWrongValues( ) {
		AbbonamentoDTO bdto = new AbbonamentoDTO("","","", null);
		assertThatThrownBy( () -> abbonamentoService.inserisciAbbonamento(bdto)  )
		.isInstanceOf(ConstraintViolationException.class)
		.hasMessageContaining("dto.nomeContenuto")
		.hasMessageContaining("dto.dataIscrizione")
		.hasMessageContaining("dto.codiceFiscale")
		.hasMessageContaining("dto.durata");
	}
	
	@Test
	@DisplayName("Provo ad inserire un abbonato già presente nel db")
	public void testPostPersonKoAlreadyPresent() {
		List<Abbonamento> l = new ArrayList<Abbonamento>();
		Abbonato a = new Abbonato(null,"Luigi","Verdi","vhfjduklosyrhdfn", l);
		abbonatoRepository.save(a);
		Contenuto c = new Contenuto(null, "Prime Video", 15.0, l);
		contenutoRepository.save(c);
		Abbonamento b = new Abbonamento(null, "02/02/2020", 5, a, c);
		abbonamentoRepository.save(b);
		
		AbbonamentoDTO bdto = new AbbonamentoDTO(
				b.getAbbonato().getCodiceFiscale(), 
				b.getContenuto().getNome(), b.getDataIscrizione(), 
				b.getDurata());
		assertThatThrownBy( () -> abbonamentoService.inserisciAbbonamento(bdto)  )
		.isInstanceOf(AlreadyInsertedException.class)
		.hasMessage(AbbonamentoErrorMessagesEnum.Fields.ABBONAMENTO_GIA_ESISTENTE);
	}
	
	@Test
	@DisplayName("Cerco un abbonamento inesistente")
	public void testGetUnknowElement() {
		assertThatThrownBy( () -> abbonamentoService.find(100000l) )
		.isInstanceOf(EntityNotFoundException.class)
		.hasMessage(AbbonamentoErrorMessagesEnum.Fields.ABBONAMENTO_NON_ESISTENTE);
	}
	
	@Test
	@DisplayName("Cerco di inserire un abbonamento ma i dati errati provocano un eccezione di tipo CostraintValidationException")
	public void testValidationKo() {
		 
		AbbonamentoDTO bdto = new AbbonamentoDTO("","Netflix","143/12/2023", -30);
		
		assertThatThrownBy( () -> abbonamentoService.inserisciAbbonamento(bdto) )
		.isInstanceOf(  ConstraintViolationException.class )
		.hasMessageContaining("Data")
		.hasMessageContaining("codiceFiscale")
		.hasMessageContaining("durata");
	}
	
	
	
	
	
}
