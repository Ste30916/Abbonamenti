package com.acme.abbonamenti.abbonamenti;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbbonamentoDTO {

	@NotBlank(message = "Codice Fiscale obbligatorio")
	@Size(min = 16, max = 16,message = "Il codice fiscale deve essere di 16 caratteri")
	private String codiceFiscale;
	@NotBlank(message = "Nome Contenuto obbligatorio")
	@Size(min = 2, max=30, message = "Il nome del contenuto deve essere minimo di 2 caratteri, massimo di 60")
	private String nomeContenuto;
	@NotBlank(message = "Data obbligatoria")
	@DateTimeFormat(pattern="dd/mm/yyyy")
	private String dataIscrizione;
	@NotNull(message = "Durata obbligatoria")
	@Min(value = 1, message = "La durata dell'abbonamento deve essere minimo 1 mese")
	private Integer durata;
}
