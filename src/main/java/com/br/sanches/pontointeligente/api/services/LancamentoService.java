package com.br.sanches.pontointeligente.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.br.sanches.pontointeligente.api.entity.Lancamento;

public interface LancamentoService {

	Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);
	
	Optional<Lancamento> buscarPorId(Long Id);
	
	Lancamento persistir(Lancamento lancamento);
	
	void remover(Long id);
}
