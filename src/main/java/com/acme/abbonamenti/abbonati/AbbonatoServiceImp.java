package com.acme.abbonamenti.abbonati;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.acme.abbonamenti.abbonamenti.Abbonamento;

@Service
@Validated
public class AbbonatoServiceImp implements AbbonatoService {
	@Autowired
	AbbonatoRepository abbonatoRepo;

	
	@Override
	public void inserisciAbbonato(@Valid AbbonatoDto dto) throws AlreadyInsertedException  {
		if(abbonatoRepo.existsByCodiceFiscale(dto.getCodiceFiscale())) throw new AlreadyInsertedException("Abbonato già inserito");
		Abbonato ab= new Abbonato();
		BeanUtils.copyProperties(dto, ab);
		abbonatoRepo.save(ab);
	}

	@Override
	public Abbonato getAbbonato(long id) {
		if(! abbonatoRepo.existsById(id)) throw new EntityNotFoundException("Abbonato non trovato");
		return abbonatoRepo.findById(id).get();
	}

	@Override
	public List<Abbonato> getAllAbbonati() {
		return  (List<Abbonato>) abbonatoRepo.findAll();
	}



	
}
