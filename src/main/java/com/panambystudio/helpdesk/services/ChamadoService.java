package com.panambystudio.helpdesk.services;

import java.util.List;

import com.panambystudio.helpdesk.domain.Chamado;
import com.panambystudio.helpdesk.domain.dtos.ChamadoDTO;

public interface ChamadoService {

	Chamado findById(Integer id);
	List<ChamadoDTO> findAll();	
}
