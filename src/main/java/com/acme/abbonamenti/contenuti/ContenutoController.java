package com.acme.abbonamenti.contenuti;

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
@RequestMapping("/contenuto")
public class ContenutoController {
	
	@Autowired
	ContenutoService contenutoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable long id) {
		Contenuto c= contenutoService.find(id);
		GetContenutoResponse resp=new GetContenutoResponse();
		BeanUtils.copyProperties(c, resp);
		return ResponseEntity.ok(resp);
	}
	@GetMapping
	public ResponseEntity<?> getAll() {
		List<Contenuto> listContenuto =contenutoService.getListaContenuti();
		List<GetContenutoResponse> listResp= new ArrayList<GetContenutoResponse>();
		for (Contenuto c : listContenuto) {
			GetContenutoResponse resp=new GetContenutoResponse();
			BeanUtils.copyProperties(c, resp);
			listResp.add(resp);			
		}
		return ResponseEntity.ok(listResp);
	}
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody ContenutoDTO dto) throws AlreadyInsertedException {
		contenutoService.inserisciContenuto(dto);	
		return ResponseEntity.ok("Contenuto inserito ");
		}
	

}
