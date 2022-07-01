package com.acme.abbonamenti.abbonati;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAbbonatoResponse {
	private String nome;
	private String cognome;
	private String codiceFiscale;

}
