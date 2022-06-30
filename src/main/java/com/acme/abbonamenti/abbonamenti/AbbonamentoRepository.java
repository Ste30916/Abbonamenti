package com.acme.abbonamenti.abbonamenti;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.acme.abbonamenti.abbonati.Abbonato;

@Repository
public interface AbbonamentoRepository extends PagingAndSortingRepository<Abbonamento, Long> {

	public boolean existsByAbbonato_idAndContenuto_idAndDataIscrizione(Long idAbbonato, Long idContenuto, String dataIscrizione);
	public Abbonamento findByAbbonato_idAndContenuto_idAndDataIscrizione(Long idAbbonato, Long idContenuto, String dataIscrizione);
	
	public List<Abbonamento> findAllByContenuto_id(Long id);
	public List<Abbonamento> findAllByAbbonato_id(Long id);
}
