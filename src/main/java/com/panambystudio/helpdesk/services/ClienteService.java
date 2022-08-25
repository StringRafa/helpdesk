package com.panambystudio.helpdesk.services;

import java.util.List;

import javax.validation.Valid;

import com.panambystudio.helpdesk.domain.Cliente;
import com.panambystudio.helpdesk.domain.dtos.ClienteDTO;

public interface ClienteService {

	Cliente findById(Integer id);
	List<ClienteDTO> findAll();
	Cliente create(ClienteDTO objDTO);
	Cliente update(Integer id, @Valid ClienteDTO objDTO);
	void delete(Integer id);
}
