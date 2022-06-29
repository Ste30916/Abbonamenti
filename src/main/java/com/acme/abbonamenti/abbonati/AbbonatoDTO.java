package com.acme.abbonamenti.abbonati;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbbonatoDTO {
	@NotBlank(message = "Nome abbonato obbligatorio")
	@Size(min = 2, max = 60,message = "Il nome dell'abbonato deve essere minimo di 2 caratteri massimo di 60")
	private String nome;
	@NotBlank(message = "Cognome abbonato obbligatorio")
	@Size(min = 2, max = 60,message = "Il cognome dell'abbonato deve essere minimo di 2 caratteri massimo di 60")
	private String cognome;
	@NotBlank(message = "Codice fiscale abbonato obbligatorio")
	@Size(min = 16, max=16, message = "Il codice fiscale deve essere di 16 caratteri")
	private String codiceFiscale; 

}
