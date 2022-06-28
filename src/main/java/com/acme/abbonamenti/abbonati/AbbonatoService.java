package com.acme.abbonamenti.abbonati;

import java.util.List;

import com.acme.abbonamenti.abbonamenti.Abbonamento;

public interface AbbonatoService { 
	void  inserisciAbbonato(AbbonatoDto dto) throws AlreadyInsertedException;
	Abbonato getAbbonato(long id);
	List<Abbonato> getAllAbbonati();

}
