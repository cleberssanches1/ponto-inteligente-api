package com.br.sanches.pontointeligente.api.repository;



//import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.br.sanches.pontointeligente.api.entity.Empresa;

/**
 * 
 * @author cleber
 *
 */
@Transactional(readOnly = true)
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
	
	Empresa findByCnpj(String cnpj);
	
}
