package com.acme.abbonamenti.abbonamenti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAbbonamentoResponse {
	private String codiceFiscale;
	private String nomeContenuto;
	private String dataIscrizione;
	private Integer durata;

}
