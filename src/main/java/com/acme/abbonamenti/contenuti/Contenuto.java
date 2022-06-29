package com.acme.abbonamenti.contenuti;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NegativeOrZero;

import com.acme.abbonamenti.abbonamenti.Abbonamento;
import com.acme.abbonamenti.abbonati.Abbonato;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor


@Entity
@Table(name = "contenuti")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public  class Contenuto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, length = 30)
	private String nome;
	@Column(nullable = false)
	private double tariffa;
	
	@ToString.Exclude
	@JsonIgnoreProperties({"contenuto"})
	@OneToMany(mappedBy = "contenuto")
	private List<Abbonamento> abbonamenti;
}
