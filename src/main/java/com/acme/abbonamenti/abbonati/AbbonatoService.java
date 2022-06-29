package com.acme.abbonamenti.abbonati;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.acme.abbonamenti.abbonamenti.Abbonamento;
import com.acme.abbonamenti.errors.AlreadyInsertedException;

@Service
@Validated
public class AbbonatoService {
	@Autowired
	AbbonatoRepository abbonatoRepo;

	public void inserisciAbbonato(@Valid AbbonatoDTO dto) throws AlreadyInsertedException  {
		if(abbonatoRepo.existsByCodiceFiscale(dto.getCodiceFiscale())) throw new AlreadyInsertedException("Abbonato già inserito");
		Abbonato ab= new Abbonato();
		BeanUtils.copyProperties(dto, ab);
		abbonatoRepo.save(ab);
	}

	public Abbonato getAbbonato(long id) {
		if(! abbonatoRepo.existsById(id)) throw new EntityNotFoundException("Abbonato non trovato");
		return abbonatoRepo.findById(id).get();
	}
	
	public Abbonato getAbbonato(String codiceFiscale) {
		if(! abbonatoRepo.existsByCodiceFiscale(codiceFiscale)) throw new EntityNotFoundException("Abbonato non trovato");
		return abbonatoRepo.findByCodiceFiscale(codiceFiscale);
	}

	public List<Abbonato> getAllAbbonati() {
		return  (List<Abbonato>) abbonatoRepo.findAll();
	}



	
}
