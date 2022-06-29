package com.acme.abbonamenti.contenuti;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ContenutoDTO {
		
		@NotBlank(message = "Il nome del contenuto è obbligatorio")
		@Size(min = 2, max = 30, message = "Il nome del contenuto deve essere minimo di 2 caratteri e massimo di 60")
		private String nome;
		
		@NotNull(message = "La tariffa è obbligatorio")
		@DecimalMin(value = "0", message = "La tariffa non può essere minore di zero")
		private Double tariffa;
		


}
