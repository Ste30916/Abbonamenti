package com.acme.abbonamenti.contenuti;

import javax.persistence.Table;

import com.acme.abbonamenti.abbonati.Abbonato;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

@Table(name = "office")
public class Office extends ContenutoImp {

	public Office(long id, String nome, double tariffa, Abbonato abbonato) {
		super(id, nome, tariffa, abbonato);
		// TODO Auto-generated constructor stub
	}


}
