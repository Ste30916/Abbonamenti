package com.acme.abbonamenti.abbonamenti;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Getter
public enum AbbonamentoErrorMessagesEnum {
	@FieldNameConstants.Include ABBONAMENTO_GIA_ESISTENTE("Abbonamento già esistente"),
	@FieldNameConstants.Include ABBONAMENTO_NON_ESISTENTE("Abbonamento non esistente");
	private String descrizione;
}
