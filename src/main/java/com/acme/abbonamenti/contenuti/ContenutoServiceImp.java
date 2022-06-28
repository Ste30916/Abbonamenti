package com.acme.abbonamenti.contenuti;

import java.nio.channels.AlreadyBoundException;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.acme.abbonamenti.abbonati.Abbonato;
import com.acme.abbonamenti.contenuti.AlreadyInsertedException;

@Service
@Validated
public class ContenutoServiceImp implements ContenutoService {
	@Autowired
	ContenutoRepository contenutoRepository;
	
	@Override
	public ContenutoImp find(long id) {
		
		if(!contenutoRepository.existsById(id)) throw new EntityNotFoundException("Contenuto non trovato");
		return contenutoRepository.findById(id).get();
	}
	
	@Override
	public void inserisciContenuto(@Valid ContenutoDTO dto) throws AlreadyInsertedException  {
		
		if(contenutoRepository.existsById(dto.getId())) throw new AlreadyInsertedException("Contenuto già esistente");
		
		ContenutoImp c = new ContenutoImp(); 
		
		BeanUtils.copyProperties(dto, c);
		
		contenutoRepository.save(c);
	}

	@Override
	public List<ContenutoImp> getListaContenuti() {
		return (List<ContenutoImp>) contenutoRepository.findAll();
	}
	

}
	
	
			