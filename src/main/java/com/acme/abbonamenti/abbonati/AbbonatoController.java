package com.acme.abbonamenti.abbonati;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abbonato")
public class AbbonatoController {
	@Autowired
	AbbonatoRepository abbonatoRepo;
	@Autowired
	AbbonatoService abbonatoService;
	
	@GetMapping
	public ResponseEntity<?> getAllAbbonati(){
		return ResponseEntity.ok(abbonatoRepo.findAll());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAbbonato(@PathVariable long id){
		return ResponseEntity.ok(abbonatoRepo.findById(id));
 
}
	@PostMapping
	public ResponseEntity<?> inserisciAbbonato(@RequestBody AbbonatoDto dto) throws AlreadyInsertedException{
		abbonatoService.inserisciAbbonato(dto);
		return ResponseEntity.ok("Abbonato inserito");
	}
	
}
