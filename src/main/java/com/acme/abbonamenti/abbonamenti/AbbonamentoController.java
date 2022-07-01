package com.acme.abbonamenti.abbonamenti;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/abbonamento")
@Tag(description = "Abbonamenti rest services", name="Abbonamenti")
public class AbbonamentoController {
	@Autowired private AbbonamentoService abbonamentoService;

	
	@Operation(summary = "Trova tutti gli abbonamenti di un contenuto",security = @SecurityRequirement(name = "bearer-authentication"))
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Abbonamenti trovati",   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Abbonamento.class)) }),  
	  @ApiResponse(responseCode = "404", description = "Abbonamenti non trovati",   content = @Content) })
	
	@GetMapping("/admin/perContenuto/{idContenuto}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAbbonatiByContenuto(@PathVariable long idContenuto) {
		List<Abbonamento> listAbbonamento =abbonamentoService.findAbbonatiByContenuto(idContenuto);
		List<GetAbbonamentoResponse> listResp = new ArrayList<GetAbbonamentoResponse>();
		for (Abbonamento b : listAbbonamento) {
			GetAbbonamentoResponse resp = new GetAbbonamentoResponse();
			BeanUtils.copyProperties(b, resp);
			resp.setCodiceFiscale(b.getAbbonato().getCodiceFiscale());
			resp.setNomeContenuto(b.getContenuto().getNome());
			listResp.add(resp);			
		}
		return ResponseEntity.ok( listResp );
	}

	
	
	@Operation(summary = "Trova tutti gli abbonamenti di un abbonato",security = @SecurityRequirement(name = "bearer-authentication"))
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Abbonamenti trovati",   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Abbonamento.class)) }),  
	  @ApiResponse(responseCode = "404", description = "Abbonamenti non trovati",   content = @Content) })
	
	
	@GetMapping("/user/perAbbonato/{idAbbonato}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getContenutiByAbbonato(@PathVariable long idAbbonato) {
		List<Abbonamento> listAbbonamento =abbonamentoService.findContenutiByAbbonato(idAbbonato);
		List<GetAbbonamentoResponse> listResp = new ArrayList<GetAbbonamentoResponse>();
		for (Abbonamento b : listAbbonamento) {
			GetAbbonamentoResponse resp = new GetAbbonamentoResponse();
			BeanUtils.copyProperties(b, resp);
			resp.setCodiceFiscale(b.getAbbonato().getCodiceFiscale());
			resp.setNomeContenuto(b.getContenuto().getNome());
			listResp.add(resp);			
		}
		return ResponseEntity.ok( listResp );
	}


	@Operation(summary = "Trova tutti gli abbonamenti",security = @SecurityRequirement(name = "bearer-authentication"))
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Abbonamento trovato",   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Abbonamento.class)) }),  
	  @ApiResponse(responseCode = "404", description = "Abbonamento non trovato",   content = @Content) })
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAll() {
		List<Abbonamento> listAbbonamento =abbonamentoService.findAll();
		List<GetAbbonamentoResponse> listResp = new ArrayList<GetAbbonamentoResponse>();
		for (Abbonamento b : listAbbonamento) {
			GetAbbonamentoResponse resp = new GetAbbonamentoResponse();
			BeanUtils.copyProperties(b, resp);
			resp.setCodiceFiscale(b.getAbbonato().getCodiceFiscale());
			resp.setNomeContenuto(b.getContenuto().getNome());
			listResp.add(resp);			
		}
		return ResponseEntity.ok( listResp );
	}
	
	
	@Operation(summary = "Trova abbonamento per id",security = @SecurityRequirement(name = "bearer-authentication"))
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Abbonamento trovato",   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Abbonamento.class)) }),  
	  @ApiResponse(responseCode = "400", description = "id non valido",   content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Abbonamento non trovato",   content = @Content) })
	
	@GetMapping("/admin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> get(@Parameter(description = "id dell'abbonamento da cercare") @PathVariable long id) {
		Abbonamento b= abbonamentoService.find(id);
		GetAbbonamentoResponse resp=new GetAbbonamentoResponse();
		BeanUtils.copyProperties(b, resp);
		resp.setCodiceFiscale(b.getAbbonato().getCodiceFiscale());
		resp.setNomeContenuto(b.getContenuto().getNome());
		return ResponseEntity.ok(resp);
	}


	@Operation(summary = "Inserisci un abbonamento",security = @SecurityRequirement(name = "bearer-authentication"))
	@ApiResponses(value = { 
	 @ApiResponse(responseCode = "200", description = "Abbonamento inserito",   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Abbonamento.class)) }),  
	 @ApiResponse(responseCode = "400", description = "Abbonamento non inserito",   content = @Content) })
	
	@PostMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> insert(@RequestBody AbbonamentoDTO dto) throws com.acme.abbonamenti.errors.AlreadyInsertedException {
		abbonamentoService.inserisciAbbonamento(dto);
		return ResponseEntity.ok(  "Abbonamento inserito"  );
	}

}
