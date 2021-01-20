package com.br.sanches.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.sanches.pontointeligente.api.entity.Empresa;
import com.br.sanches.pontointeligente.api.repository.EmpresaRepository;
import com.br.sanches.pontointeligente.api.services.EmpresaServices;

@Service
public class EmpresaServiceImpl implements EmpresaServices{
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
   
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Override
	public Optional<Empresa> buscarPorCnpj(String cnpj) {
		log.info("Buscando empresa por cnpj {}", cnpj);
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj)) ;
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		log.info("persistindo: {}", empresa);
		return empresaRepository.save(empresa);
	}

}
