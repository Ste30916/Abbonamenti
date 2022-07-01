package com.acme.abbonamenti.abbonati;

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

import com.acme.abbonamenti.abbonamenti.Abbonamento;
import com.acme.abbonamenti.errors.AlreadyInsertedException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/abbonato")
public class AbbonatoController {

	@Autowired
	AbbonatoService abbonatoService;
	
	@Operation(summary = "Trova tutti gli abbonati",security = @SecurityRequirement(name = "bearer-authentication"))
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Abbonato trovato",   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Abbonato.class)) }),  
	  @ApiResponse(responseCode = "404", description = "Abbonato non trovato",   content = @Content) })
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
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
	
	@Operation(summary = "Trova abbonato per id",security = @SecurityRequirement(name = "bearer-authentication"))
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Abbonato trovato",   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Abbonato.class)) }),  
	  @ApiResponse(responseCode = "400", description = "id non valido",   content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Abbonato non trovato",   content = @Content) })
	
	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAbbonato(@PathVariable long id){
		Abbonato a= abbonatoService.find(id);
		GetAbbonatoResponse resp= new GetAbbonatoResponse();
		BeanUtils.copyProperties(a, resp);
		return ResponseEntity.ok(resp);
	}
	
	@Operation(summary = "Inserisci un abbonato",security = @SecurityRequirement(name = "bearer-authentication"))
	@ApiResponses(value = { 
	 @ApiResponse(responseCode = "200", description = "Abbonato inserito",   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Abbonato.class)) }),  
	 @ApiResponse(responseCode = "400", description = "Abbonato non inserito",   content = @Content) })
	
	@PostMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> inserisciAbbonato(@RequestBody AbbonatoDTO dto) throws AlreadyInsertedException{
		abbonatoService.inserisciAbbonato(dto);
		return ResponseEntity.ok("Abbonato inserito");
	}
	
}
