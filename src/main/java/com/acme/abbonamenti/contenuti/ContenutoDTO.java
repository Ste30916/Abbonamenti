package com.acme.abbonamenti.contenuti;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ContenutoDTO {
		
		private long id;
		@NotBlank(message = "Nome del servizio obbligatorio")
		@Size(min = 2, max = 60, message = "Il nome del contenuto deve essere minimo di 2 caratteri e massimo di 60")
		private String nome;
		
		@NotBlank(message = "Il tariffa è obbligatorio")
		@Size(min = 0, message = "La tariffa non può essere uguale o minore di zero")
		private double tariffa;
		


}
