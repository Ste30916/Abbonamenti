package com.acme.abbonamenti.abbonati;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Getter
public enum AbbonatoErrorMessagesEnum {
	@FieldNameConstants.Include ABBONATO_GIA_ESISTENTE("Abbonato già esistente"),
	@FieldNameConstants.Include ABBONATO_NON_ESISTENTE("Abbonato non esistente");
	private String descrizione;
	
}
