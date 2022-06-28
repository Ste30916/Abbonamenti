package com.acme.abbonamenti.abbonamenti;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "abbonamenti")
@Entity
public class Abbonamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String dataIscrizione;
	
	private int durata;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnoreProperties({"abbonamenti"})
	@ManyToOne
	private Abbonato abbonato;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnoreProperties({"abbonamenti"})
	@ManyToOne
	private Contenuto contenuto;

}
