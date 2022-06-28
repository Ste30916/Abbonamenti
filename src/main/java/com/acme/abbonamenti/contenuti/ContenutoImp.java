package com.acme.abbonamenti.contenuti;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NegativeOrZero;

import com.acme.abbonamenti.abbonati.Abbonato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


@Entity
@Table(name = "Contenuti")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public  abstract class ContenutoImp implements Contenuto {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	private String nome;
	private double tariffa;
	
	@OneToMany
	private Abbonato abbonato;
}
