package com.panambystudio.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.panambystudio.helpdesk.domain.Chamado;
import com.panambystudio.helpdesk.domain.Cliente;
import com.panambystudio.helpdesk.domain.Tecnico;
import com.panambystudio.helpdesk.domain.enums.Perfil;
import com.panambystudio.helpdesk.domain.enums.Prioridade;
import com.panambystudio.helpdesk.domain.enums.Status;
import com.panambystudio.helpdesk.repositories.ChamadoRepository;
import com.panambystudio.helpdesk.repositories.ClienteRepository;
import com.panambystudio.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Tecnico tec1 = new Tecnico(null, "Valdir César", "08608998025", "valdir@gmail.com", "123");		
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Linus Torvalds", "95194377037", "linus@gmail.com", "123");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		
		chamadoRepository.saveAll(Arrays.asList(c1));
		
	}

}
