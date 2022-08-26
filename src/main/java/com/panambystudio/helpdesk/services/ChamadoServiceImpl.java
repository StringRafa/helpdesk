package com.panambystudio.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panambystudio.helpdesk.domain.Chamado;
import com.panambystudio.helpdesk.domain.Cliente;
import com.panambystudio.helpdesk.domain.Tecnico;
import com.panambystudio.helpdesk.domain.dtos.ChamadoDTO;
import com.panambystudio.helpdesk.domain.enums.Perfil;
import com.panambystudio.helpdesk.domain.enums.Prioridade;
import com.panambystudio.helpdesk.domain.enums.Status;
import com.panambystudio.helpdesk.repositories.ChamadoRepository;
import com.panambystudio.helpdesk.security.UserSS;
import com.panambystudio.helpdesk.services.exceptions.AuthorizationException;
import com.panambystudio.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoServiceImpl implements ChamadoService{

	@Autowired
	private ChamadoRepository repository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(Integer id) {
		
		Optional<Chamado> obj = repository.findById(id);
		
		UserSS user = UserService.authenticated();
		
		if(obj.isPresent()) {
			
			if (user == null || !user.hasRole(Perfil.TECNICO) && !obj.get().getCliente().getId().equals(user.getId())) {
				throw new AuthorizationException("Acesso negado");
			}
		}
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}

	@Override
	public List<ChamadoDTO> findAll() {
		
		List<Chamado> chamado = repository.findAll();
		
		return chamado.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
	}

	@Override
	public Chamado create(ChamadoDTO objDTO) {		
		return repository.save(newChamado(objDTO));
	}
	
	private Chamado newChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		
		Chamado chamado = new Chamado();
		if(obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		
		if(obj.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		
		return chamado;
	}

	@Override
	public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
		
		objDTO.setId(id);
		
		Chamado oldObj = findById(id);
		
		oldObj = newChamado(objDTO);
		
		return repository.save(oldObj);
	}
}
