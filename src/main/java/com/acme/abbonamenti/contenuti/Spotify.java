package com.acme.abbonamenti.contenuti;

import javax.persistence.Table;

import com.acme.abbonamenti.abbonati.Abbonato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

@Table(name = "spotify")
public class Spotify extends ContenutoImp {public Spotify(long id, String nome, double tariffa, Abbonato abbonato) {
		super(id, nome, tariffa, abbonato);
		// TODO Auto-generated constructor stub
	}


}
