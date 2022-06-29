package com.acme.abbonamenti.abbonamenti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abbonamento")
public class AbbonamentoController {
	@Autowired private AbbonamentoService abbonamentoService;
	
	@GetMapping("/perContenuto/{idContenuto}")
	public ResponseEntity<?> getAbbonatiByContenuto(@PathVariable long idContenuto) {
		return ResponseEntity.ok( abbonamentoService.findAbbonatiByContenuto(idContenuto) );
	}
	
	@GetMapping("/perAbbonato/{idAbbonato}")
	public ResponseEntity<?> getContenutiByAbbonato(@PathVariable long idAbbonato) {
		return ResponseEntity.ok( abbonamentoService.findContenutiByAbbonato(idAbbonato) );
	}
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok( abbonamentoService.findAll() );
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable long id) {
		return ResponseEntity.ok(abbonamentoService.find(id));
	}
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody AbbonamentoDTO dto) throws com.acme.abbonamenti.errors.AlreadyInsertedException {
		abbonamentoService.inserisciAbbonato(dto);
		return ResponseEntity.ok(  "Abbonamento inserito"  );
	}
	
}
