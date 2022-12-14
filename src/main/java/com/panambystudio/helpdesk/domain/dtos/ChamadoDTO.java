package com.panambystudio.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.panambystudio.helpdesk.domain.Chamado;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChamadoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAbertura = LocalDate.now();
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFechamento;
	@NotNull(message = "O campo PRIORIDADE é obrigatório!")
	private Integer prioridade;
	@NotNull(message = "O campo STATUS é obrigatório!")
	private Integer status;
	@NotNull(message = "O campo TÍTULO é obrigatório!")
	private String titulo;
	@NotNull(message = "O campo OBSERVAÇÕES é obrigatório!")
	private String observacoes;
	@NotNull(message = "O campo TÉCNICO é obrigatório!")
	private Integer tecnico;
	@NotNull(message = "O campo CLIENTE é obrigatório!")
	private Integer cliente;
	private String nomeTecnico;
	private String nomeCliente;
	
	public ChamadoDTO(Chamado obj) {
		super();
		this.id = obj.getId();
		this.dataAbertura = obj.getDataAbertura();
		this.dataFechamento = obj.getDataFechamento();
		this.prioridade = obj.getPrioridade().getCodigo();
		this.status = obj.getStatus().getCodigo();
		this.titulo = obj.getTitulo();
		this.observacoes = obj.getObservacoes();
		this.tecnico = obj.getTecnico().getId();
		this.cliente = obj.getCliente().getId();
		this.nomeTecnico = obj.getTecnico().getNome();
		this.nomeCliente = obj.getCliente().getNome();
	}
}
