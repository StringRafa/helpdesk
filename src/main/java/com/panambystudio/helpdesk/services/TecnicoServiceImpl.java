package com.panambystudio.helpdesk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.panambystudio.helpdesk.domain.Pessoa;
import com.panambystudio.helpdesk.domain.Tecnico;
import com.panambystudio.helpdesk.domain.dtos.TecnicoDTO;
import com.panambystudio.helpdesk.repositories.PessoaRepository;
import com.panambystudio.helpdesk.repositories.TecnicoRepository;
import com.panambystudio.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.panambystudio.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoServiceImpl implements TecnicoService{

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public Tecnico findById(Integer id) {
		
		Optional<Tecnico> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}

	@Override
	public List<TecnicoDTO> findAll() {
		
		List<Tecnico> tecnico = repository.findAll();
		
		return tecnico.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
	}

	@Override
	public Tecnico create(TecnicoDTO objDTO) {
		
		objDTO.setId(null);
		
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		
		Tecnico newObj = new Tecnico(objDTO);
		
		return repository.save(newObj);
	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}

	@Override
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		
		objDTO.setId(id);
		
		Tecnico oldObj = findById(id);
		
		validaPorCpfEEmail(objDTO);
		
		oldObj = new Tecnico(objDTO);
		
		return repository.save(oldObj);
	}

	@Override
	public void delete(Integer id) {
		
		Tecnico obj = findById(id);
		
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordem de serviço e não pode ser deletado!");
		}
			
		repository.deleteById(id);
	}

}
