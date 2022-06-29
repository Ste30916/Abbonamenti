package com.acme.abbonamenti.abbonamenti;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.acme.abbonamenti.abbonati.Abbonato;
import com.acme.abbonamenti.abbonati.AbbonatoRepository;
import com.acme.abbonamenti.abbonati.AbbonatoService;
import com.acme.abbonamenti.contenuti.Contenuto;
import com.acme.abbonamenti.contenuti.ContenutoService;
import com.acme.abbonamenti.errors.AlreadyInsertedException;

@Service
@Validated
public class AbbonamentoService {
	@Autowired private AbbonamentoRepository abbonamentoRepo;
	@Autowired private AbbonatoService abbonatoService;
	@Autowired private ContenutoService contenutoService;
	
	public List<Abbonamento> findAbbonatiByContenuto (Long idContenuto) {
		return abbonamentoRepo.findAllByContenuto_id(idContenuto);
	}
	
	public List<Abbonamento> findContenutiByAbbonato (Long idAbbonato) {
		return abbonamentoRepo.findAllByAbbonato_id(idAbbonato);
	}
	
	public List<Abbonamento> findAll () {
		return (List<Abbonamento>) abbonamentoRepo.findAll();
	}
	
	public void inserisciAbbonato(@Valid AbbonamentoDTO dto) 
			throws AlreadyInsertedException, EntityNotFoundException {
		Abbonato abbonato = abbonatoService.getAbbonato(dto.getCodiceFiscale());
		Contenuto contenuto = contenutoService.find(dto.getNomeContenuto());
		
		if (abbonamentoRepo.existsByAbbonato_idAndContenuto_id
				(abbonato.getId(), contenuto.getId())) throw new AlreadyInsertedException
				("Abbonamento già inserito");
		
		Abbonamento abbonamento = new Abbonamento();
		BeanUtils.copyProperties(dto, abbonamento);
		abbonamento.setAbbonato(abbonato);
		abbonamento.setContenuto(contenuto);
		abbonamentoRepo.save(abbonamento);
	}
	
}