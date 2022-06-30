package com.acme.abbonamenti.abbonamenti;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.acme.abbonamenti.abbonati.Abbonato;
import com.acme.abbonamenti.abbonati.AbbonatoDTO;
import com.acme.abbonamenti.abbonati.AbbonatoRepository;
import com.acme.abbonamenti.contenuti.Contenuto;
import com.acme.abbonamenti.contenuti.ContenutoDTO;
import com.acme.abbonamenti.contenuti.ContenutoRepository;
import com.acme.abbonamenti.errors.AlreadyInsertedException;
import com.acme.abbonamenti.general.TestBase;

public class AbbonamentiServiceTest extends TestBase {
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
		Abbonato a = new Abbonato(null,"Luigi","Verdi","vhfjduklosyrhdfn", l);
		abbonatoRepository.save(a);
		Contenuto c = new Contenuto(null, "Prime Video", 15.0, l);
		contenutoRepository.save(c);
		
		AbbonamentoDTO bdto = new AbbonamentoDTO(
				"vhfjduklosyrhdfn", "Prime Video", "06/11/2021", 5);
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
}
