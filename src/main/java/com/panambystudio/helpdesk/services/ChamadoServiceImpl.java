package com.panambystudio.helpdesk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panambystudio.helpdesk.domain.Chamado;
import com.panambystudio.helpdesk.domain.dtos.ChamadoDTO;
import com.panambystudio.helpdesk.repositories.ChamadoRepository;
import com.panambystudio.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoServiceImpl implements ChamadoService{

	@Autowired
	private ChamadoRepository repository;
	
	public Chamado findById(Integer id) {
		
		Optional<Chamado> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}

	@Override
	public List<ChamadoDTO> findAll() {
		
		List<Chamado> chamado = repository.findAll();
		
		return chamado.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
	}
}
