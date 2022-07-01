package com.acme.abbonamenti.contenuti;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.abbonamenti.abbonati.Abbonato;
import com.acme.abbonamenti.errors.AlreadyInsertedException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/contenuto")
public class ContenutoController {
	
	@Autowired
	ContenutoService contenutoService;
	
	@Operation(summary = "Trova contenuto per id",security = @SecurityRequirement(name = "bearer-authentication"))
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Contenuto trovato",   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Contenuto.class)) }),  
	  @ApiResponse(responseCode = "400", description = "id non valido",   content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Contenuto non trovato",   content = @Content) })
	
	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> get(@PathVariable long id) {
		Contenuto c= contenutoService.find(id);
		GetContenutoResponse resp=new GetContenutoResponse();
		BeanUtils.copyProperties(c, resp);
		return ResponseEntity.ok(resp);
	}
	
	@Operation(summary = "Trova tutti i contenuti",security = @SecurityRequirement(name = "bearer-authentication"))
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Contenuto trovato",   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Contenuto.class)) }),  
	  @ApiResponse(responseCode = "404", description = "Contenuto non trovato",   content = @Content) })
	
	@GetMapping()
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
	
	@Operation(summary = "Inserisci un contenuto",security = @SecurityRequirement(name = "bearer-authentication"))
	@ApiResponses(value = { 
	 @ApiResponse(responseCode = "200", description = "Contenuto inserito",   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Contenuto.class)) }),  
	 @ApiResponse(responseCode = "400", description = "Contenuto non inserito",   content = @Content) })
	
	@PostMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> insert(@RequestBody ContenutoDTO dto) throws AlreadyInsertedException {
		contenutoService.inserisciContenuto(dto);	
		return ResponseEntity.ok("Contenuto inserito ");
	}
	

}
