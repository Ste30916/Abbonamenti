package com.acme.abbonamenti.contenuti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetContenutoResponse {
	private String nome;
	private Double tariffa;
	

}
