package com.br.sanches.pontointeligente.api.services;

import java.util.Optional;

import com.br.sanches.pontointeligente.api.entity.Empresa;

public interface EmpresaServices {

	/**
	 * Retorna uma empresa dado um CNPJ
	 * @param cnpj String
	 * @return Optional<Empresa>
	 */
	Optional<Empresa> buscarPorCnpj(String cnpj) ;
	/**
	 * Cadastra uma nova empresa
	 * @param empresa Empresa
	 * @return Empresa
	 */
	Empresa persistir(Empresa empresa);
	
}
