package com.br.sanches.pontointeligente.api.services;

 
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.br.sanches.pontointeligente.api.entity.Funcionario;
import com.br.sanches.pontointeligente.api.repository.FuncionarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {
	
	@MockBean
	private FuncionarioRepository funcionarioRepository;
	
    @Autowired
    private FuncionarioService funcionarioService;
	    
    @Before
    public void setUp() throws Exception{
    	
    	BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());    	    	
		BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());		
		BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());		
    	BDDMockito.given(this.funcionarioRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(new Funcionario()));
    }
    
    @Test
    public void testPersistirFuncionario() throws Exception{
    	setUp();
    	Funcionario funcionario = this.funcionarioService.persistir(new Funcionario());
    	assertNotNull(funcionario);    	
    }
    
    @Test
    public void testBuscarFuncionarioPorId() throws Exception{
    	setUp();
    	Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(1L);
    	
    	assertTrue(funcionario.isPresent());
    }
    
    @Test
    public void testBuscarFuncionarioPorCpf() throws Exception{
    	setUp();
    	Optional<Funcionario> funcionario = this.funcionarioService.buscarPorCpf("24291173474");
    	assertTrue(funcionario.isPresent());	
    }
    
    @Test
    public void testBuscarFuncionarioPorEmail() throws Exception{
    	setUp();
    	Optional<Funcionario> funcionario = this.funcionarioService.buscarPorEmail("email@email.com");
    	assertTrue(funcionario.isPresent()); 	
    }
    
}
