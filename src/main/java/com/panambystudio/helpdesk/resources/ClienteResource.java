package com.panambystudio.helpdesk.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.panambystudio.helpdesk.domain.dtos.ClienteDTO;
import com.panambystudio.helpdesk.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){
		
		return ResponseEntity.ok().body(new ClienteDTO(service.findById(id)));
	}
	
	@PreAuthorize("hasAnyRole('TECNICO')")
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll(){
		
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@PreAuthorize("hasAnyRole('TECNICO')")
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO){
		
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(service.create(objDTO).getId()).toUri()).build();
	}
	
	@PreAuthorize("hasAnyRole('TECNICO')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO){
		
		return ResponseEntity.ok().body(new ClienteDTO(service.update(id, objDTO)));
	}
	
	@PreAuthorize("hasAnyRole('TECNICO')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id){
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
