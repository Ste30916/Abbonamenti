package com.acme.abbonamenti.contenuti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.abbonamenti.errors.AlreadyInsertedException;


@RestController
@RequestMapping("/contenuto")
public class ContenutoController {
	
	@Autowired
	ContenutoService contenutoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable long id) {
		return ResponseEntity.ok(contenutoService.find(id));
	}
	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(contenutoService.getListaContenuti());
	}
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody ContenutoDTO dto) throws AlreadyInsertedException {
		contenutoService.inserisciContenuto(dto);	
		return ResponseEntity.ok("Contenuto inserito ");
		}
	

}
