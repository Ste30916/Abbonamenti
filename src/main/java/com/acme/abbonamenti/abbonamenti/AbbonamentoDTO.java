package com.acme.abbonamenti.abbonamenti;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
	@Pattern(regexp = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", message= "Formato data non valido")
	private String dataIscrizione;
	@NotNull(message = "Durata obbligatoria")
	@Min(value = 1, message = "La durata dell'abbonamento deve essere minimo 1 mese")
	private Integer durata;
}
