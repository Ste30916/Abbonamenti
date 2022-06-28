package com.acme.abbonamenti.contenuti;

import javax.persistence.Table;
import javax.validation.constraints.NegativeOrZero;

import com.acme.abbonamenti.abbonati.Abbonato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Table(name = "netflix")
public class Netflix extends ContenutoImp {

	
	public Netflix(long id, String nome, double tariffa, Abbonato abbonato) {
		super(id, nome, tariffa, abbonato);
		// TODO Auto-generated constructor stub
	}

}
