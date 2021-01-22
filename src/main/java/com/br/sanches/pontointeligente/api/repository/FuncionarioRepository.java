package com.br.sanches.pontointeligente.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.br.sanches.pontointeligente.api.entity.Funcionario;

/**
 * 
 * @author cleber
 *
 */
@Transactional(readOnly = false)
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{	
	Funcionario findByCpf(String cpf);
	Funcionario findByEmail(String email);
	Funcionario findByCpfOrEmail(String cpf, String email);
}
