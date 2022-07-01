package com.acme.abbonamenti.abbonati;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
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
@RequestMapping("/abbonato")
public class AbbonatoController {

	@Autowired
	AbbonatoService abbonatoService;
	
	@GetMapping
	public ResponseEntity<?> getAllAbbonati(){
		List<Abbonato> listAbbonato=abbonatoService.getAllAbbonati();
		List<GetAbbonatoResponse> listResp= new ArrayList<GetAbbonatoResponse>();
		for (Abbonato a : listAbbonato) {
			GetAbbonatoResponse resp= new GetAbbonatoResponse();
			BeanUtils.copyProperties(a, resp);
			listResp.add(resp);			
		}
		return ResponseEntity.ok(listResp);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAbbonato(@PathVariable long id){
		Abbonato a= abbonatoService.getAbbonato(id);
		GetAbbonatoResponse resp= new GetAbbonatoResponse();
		BeanUtils.copyProperties(a, resp);
		return ResponseEntity.ok(resp);
 
}
	@PostMapping
	public ResponseEntity<?> inserisciAbbonato(@RequestBody AbbonatoDTO dto) throws AlreadyInsertedException{
		abbonatoService.inserisciAbbonato(dto);
		return ResponseEntity.ok("Abbonato inserito");
	}
	
}
