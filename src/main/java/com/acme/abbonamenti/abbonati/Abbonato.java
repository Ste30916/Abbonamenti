package com.acme.abbonamenti.abbonati;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.acme.abbonamenti.abbonamenti.Abbonamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "abbonati")
public class Abbonato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nome;
	
	private String cognome;
	
	private String codiceFiscale;
	
	@ToString.Exclude
	@JsonIgnoreProperties({"abbonato"})
	@OneToMany(mappedBy = "abbonato")
	private List<Abbonamento> abbonamenti;

}


