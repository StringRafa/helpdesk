package com.panambystudio.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panambystudio.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
