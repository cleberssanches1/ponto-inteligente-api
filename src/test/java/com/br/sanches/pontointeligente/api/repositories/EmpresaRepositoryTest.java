package com.br.sanches.pontointeligente.api.repositories;

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
import com.br.sanches.pontointeligente.api.repository.EmpresaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String CNPJ = "51463645000100";
	
	@Before
	public void setUp() throws Exception{
		Empresa empresa = new Empresa();
		
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj(CNPJ);
		
		this.empresaRepository.save(empresa);
		
		System.out.println("Empresa " + empresa.getCnpj() + "incluída.");
	}
	
	@After
	public void tearDown(){
		this.empresaRepository.deleteAll();
		System.out.println("Empresas excluídas.");
	}
	
	@Test
	public void testBuscarPorCnpj() throws Exception{
		
		setUp();
		Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);		
		Assert.assertEquals(CNPJ, empresa.getCnpj());
		tearDown();
	}
	
}
