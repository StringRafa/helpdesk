package com.panambystudio.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panambystudio.helpdesk.domain.Chamado;

public interface PessoaRepository extends JpaRepository<Chamado, Integer>{

}
