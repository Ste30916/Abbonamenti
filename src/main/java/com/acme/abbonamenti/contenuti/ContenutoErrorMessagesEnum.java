package com.acme.abbonamenti.contenuti;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Getter
public enum ContenutoErrorMessagesEnum {
	@FieldNameConstants.Include CONTENUTO_GIA_ESISTENTE("Contenuto già esistente"),
	@FieldNameConstants.Include CONTENUTO_NON_ESISTENTE("Contenuto non esistente");
	private String descrizione;
}
