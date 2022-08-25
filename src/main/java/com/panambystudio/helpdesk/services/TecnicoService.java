package com.panambystudio.helpdesk.services;

import java.util.List;

import javax.validation.Valid;

import com.panambystudio.helpdesk.domain.Tecnico;
import com.panambystudio.helpdesk.domain.dtos.TecnicoDTO;

public interface TecnicoService {

	Tecnico findById(Integer id);
	List<TecnicoDTO> findAll();
	Tecnico create(TecnicoDTO objDTO);
	Tecnico update(Integer id, @Valid TecnicoDTO objDTO);
	void delete(Integer id);
}
