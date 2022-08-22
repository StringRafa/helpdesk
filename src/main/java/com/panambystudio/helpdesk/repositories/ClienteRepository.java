package com.panambystudio.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panambystudio.helpdesk.domain.Pessoa;

public interface ClienteRepository extends JpaRepository<Pessoa, Integer>{

}