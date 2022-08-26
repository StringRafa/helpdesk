package com.panambystudio.helpdesk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.panambystudio.helpdesk.domain.Cliente;
import com.panambystudio.helpdesk.domain.Pessoa;
import com.panambystudio.helpdesk.domain.dtos.ClienteDTO;
import com.panambystudio.helpdesk.domain.enums.Perfil;
import com.panambystudio.helpdesk.repositories.ClienteRepository;
import com.panambystudio.helpdesk.repositories.PessoaRepository;
import com.panambystudio.helpdesk.security.UserSS;
import com.panambystudio.helpdesk.services.exceptions.AuthorizationException;
import com.panambystudio.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.panambystudio.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente findById(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.TECNICO) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}

	@Override
	public List<ClienteDTO> findAll() {
		
		List<Cliente> cliente = repository.findAll();
		
		return cliente.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
	}

	@Override
	public Cliente create(ClienteDTO objDTO) {
		
		objDTO.setId(null);
		
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		
		Cliente newObj = new Cliente(objDTO);
		
		return repository.save(newObj);
	}

	private void validaPorCpfEEmail(ClienteDTO objDTO) {
		
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
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		
		objDTO.setId(id);
		
		Cliente oldObj = findById(id);
		
		validaPorCpfEEmail(objDTO);
		
		oldObj = new Cliente(objDTO);
		
		return repository.save(oldObj);
	}

	@Override
	public void delete(Integer id) {
		
		Cliente obj = findById(id);
		
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordem de serviço e não pode ser deletado!");
		}
			
		repository.deleteById(id);
	}

}
