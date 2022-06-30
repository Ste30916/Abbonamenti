package com.acme.abbonamenti.contenuti;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.acme.abbonamenti.errors.AlreadyInsertedException;

@Service
@Validated
public class ContenutoService {
	@Autowired
	ContenutoRepository contenutoRepository;
	
	public Contenuto find(long id) {
		
		if(!contenutoRepository.existsById(id)) throw new EntityNotFoundException(ContenutoErrorMessagesEnum.Fields.CONTENUTO_NON_ESISTENTE);
		return contenutoRepository.findById(id).get();
	}
	
	public Contenuto find(String nomeContenuto) {
		
		if(!contenutoRepository.existsByNome(nomeContenuto)) throw new EntityNotFoundException(ContenutoErrorMessagesEnum.Fields.CONTENUTO_NON_ESISTENTE);
		return contenutoRepository.findByNome(nomeContenuto);
	}
	
	public void inserisciContenuto(@Valid ContenutoDTO dto) throws AlreadyInsertedException  {
		if(contenutoRepository.existsByNome(dto.getNome())) throw new AlreadyInsertedException(ContenutoErrorMessagesEnum.Fields.CONTENUTO_GIA_ESISTENTE);
		
		Contenuto c = new Contenuto(); 
		BeanUtils.copyProperties(dto, c);
		contenutoRepository.save(c);
	}

	public List<Contenuto> getListaContenuti() {
		return (List<Contenuto>) contenutoRepository.findAll();
	}
	

}
	
	
			