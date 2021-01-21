package com.br.sanches.pontointeligente.api.repository;


import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.br.sanches.pontointeligente.api.entity.Lancamento;

@Transactional(readOnly = false)
@NamedQueries({
		@NamedQuery(name = "LancamentoRepository.findByFuncionarioId",
				query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionario.id = :funcionarioId")})
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	/**
	 * 
	 * @param funcionarioId
	 * @return
	 */
	List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);
	/**
	 * Paginação
	 * @param funcionarioId
	 * @param pageable
	 * @return
	 */
	Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);

}
