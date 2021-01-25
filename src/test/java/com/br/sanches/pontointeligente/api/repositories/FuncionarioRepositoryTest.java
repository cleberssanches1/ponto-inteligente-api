package com.br.sanches.pontointeligente.api.repositories;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.br.sanches.pontointeligente.api.entity.Empresa;
import com.br.sanches.pontointeligente.api.entity.Funcionario;
import com.br.sanches.pontointeligente.api.enums.PerfilEnum;
import com.br.sanches.pontointeligente.api.repository.EmpresaRepository;
import com.br.sanches.pontointeligente.api.repository.FuncionarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	private static final String CPF = "24291173474";
	private  static final String EMAIl = "email@email.com";	
	private static final String CNPJ = "51463645000100";
	
	@After
	public void tearDown(){
		this.empresaRepository.deleteAll();
		System.out.println("Empresas excluídas.");
		
		this.funcionarioRepository.deleteAll();
	}
	
	public Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();
		
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj(CNPJ);
		
		return empresa;
	}
	
	@Before
	public void setUp(){
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		this.funcionarioRepository.save(obterDadosFuncionario(empresa));
	}
	
	public Funcionario obterDadosFuncionario(Empresa empresa) {
		
		Funcionario funcionario = new Funcionario();
		funcionario.setEmpresa(empresa);
		funcionario.setCpf(CPF);
		funcionario.setEmail(EMAIl);
		funcionario.setNome("Cleber");
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		funcionario.setQtdHorasAlmoco(2.0F);
		funcionario.setQtdHorasTrabalhoDia(8.0F);
		funcionario.setSenha("senha");
		funcionario.setValorHora(new BigDecimal(78.00F));
		
		return funcionario;
	}
	
	
	@Test
	public void testBuscarFuncionarioPorEmail() throws Exception{
		
		setUp();
		
		Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIl);
		Assert.assertEquals(EMAIl, funcionario.getEmail());

		//tearDown();
	}
	
	
	@Test
	public void testBuscarFuncionarioPorCpf() throws Exception{
		
		setUp();
		
		Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);
		Assert.assertEquals(CPF, funcionario.getCpf());

		tearDown();
	}
	
	@Test
	public void testBuscarFuncionarioPorEmailCpf() throws Exception{
		
		setUp();
		
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF,EMAIl);
		Assert.assertNotNull(funcionario);

		tearDown();
	}
	
	@Test
	public void testBuscarFuncionarioPorEmailCpfEmailInvalido() throws Exception{
		
		setUp();
		
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "email@invalido.com");
		Assert.assertNotNull(funcionario);

		tearDown();
	}
	
	@Test
	public void testBuscarFuncionarioPorEmailCpfCpfInvalido() throws Exception{
		
		setUp();
		
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail("30039779890", "EMAIl");
		Assert.assertNull(funcionario);

		tearDown();
	}
	
	
}
