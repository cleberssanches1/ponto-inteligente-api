package com.br.sanches.pontointeligente.api.repositories;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.br.sanches.pontointeligente.api.entity.Empresa;
import com.br.sanches.pontointeligente.api.entity.Funcionario;
import com.br.sanches.pontointeligente.api.entity.Lancamento;
import com.br.sanches.pontointeligente.api.enums.PerfilEnum;
import com.br.sanches.pontointeligente.api.enums.TipoEnum;
import com.br.sanches.pontointeligente.api.repository.EmpresaRepository;
import com.br.sanches.pontointeligente.api.repository.FuncionarioRepository;
import com.br.sanches.pontointeligente.api.repository.LancamentoRepository;
import com.br.sanches.pontointeligente.api.utils.SenhaUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	private static final String CPF = "24291173474";
	private  static final String EMAIl = "email@email.com";	
	private static final String CNPJ = "51463645000100";	
	private Long funcionarioId;
	
	@Test
	public void testarBuscarLancamentosFuncionarioId() throws Exception{
		setUp();
		
		List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
		assertEquals(2, lancamentos.size());
		tearDown();
	}
	
	@Test
	public void testarBuscarLancamentosFuncionarioIdPaginado() throws Exception{
		setUp();
		PageRequest page = PageRequest.of(0, 10);
		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);
		assertEquals(2, lancamentos.getSize());
		tearDown();
	}
	
	@Before
	public void setUp() throws Exception{
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		Funcionario funcionario = funcionarioRepository.save(obterDadosFuncionario(empresa));
		
		this.funcionarioId = funcionario.getId();
		
		Lancamento lancamento = this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
		
		lancamento.toString();
		
		lancamento = this.lancamentoRepository.save(obterDadosLancamentos(funcionario));	
		lancamento.toString();		
	}
	
	public Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();		
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj(CNPJ);		
		return empresa;
	}
	
	private Lancamento obterDadosLancamentos(Funcionario funcionario) {	 
		Lancamento lancamento = new Lancamento();
		lancamento.setFuncionario(funcionario);	
		lancamento.setDescricao("almoço");
		lancamento.setLocalizacao("são paulo");
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		lancamento.setData(new Date());	
				
		return lancamento;
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
		funcionario.setSenha(SenhaUtils.gerarBCrypt("123456"));
		funcionario.setValorHora(new BigDecimal(78.00F));
		
		return funcionario;
	}
	
	@After
	public void tearDown() throws Exception{
		this.empresaRepository.deleteAll();		
		this.funcionarioRepository.deleteAll();
		this.lancamentoRepository.deleteAll();
		System.out.println("Empresas, funcionários e lancamentos excluídos.");		
	}
		
}
