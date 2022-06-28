package com.acme.abbonamenti.contenuti;

import com.acme.abbonamenti.abbonati.Abbonato;

public interface Contenuto {

	Abbonato getAbbonato();

	long getId();

	String getNome();

	double getTariffa();

	void setAbbonato(Abbonato abbonato);

	void setId(long id);

	void setNome(String nome);

	void setTariffa(double tariffa);

}