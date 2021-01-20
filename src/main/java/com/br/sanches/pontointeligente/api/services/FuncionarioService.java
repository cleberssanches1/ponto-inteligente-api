package com.br.sanches.pontointeligente.api.services;

import java.util.Optional;

import com.br.sanches.pontointeligente.api.entity.Funcionario;

/**
 * 
 * @author cleber
 *
 */
public interface FuncionarioService {

	/**
	 * 
	 * @param funcionario
	 * @return
	 */
	Funcionario persistir(Funcionario funcionario);

	/**
	 * 
	 * @param cpf
	 * @return
	 */
	Optional<Funcionario> buscarPorCpf(String cpf);

	/**
	 * 
	 * @param email
	 * @return
	 */
	Optional<Funcionario> buscarPorEmail(String email);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Optional<Funcionario> buscarPorId(Long id);
}
