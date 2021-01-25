package com.br.sanches.pontointeligente.api.services;
 
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
 
import com.br.sanches.pontointeligente.api.entity.Lancamento;
 
import com.br.sanches.pontointeligente.api.repository.LancamentoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

	@MockBean
	private LancamentoRepository lancamentoRepository;
	
    @Autowired
    private LancamentoService lancamentoService;
	    
    @Before
    public void setUp() throws Exception{    	
    	BDDMockito.given(this.lancamentoRepository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
    	.willReturn(new PageImpl<Lancamento>(new ArrayList<Lancamento>()));    	
    	BDDMockito.given(this.lancamentoRepository.save(Mockito.any(Lancamento.class))).willReturn(new Lancamento());    			
		BDDMockito.given(this.lancamentoRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(new Lancamento()));    	
    }
     
    @Test
    public void buscarLancamentoPorFuncionarioId() throws Exception{   
    	setUp();
    	PageRequest page = PageRequest.of(0, 10);
    	Page<Lancamento> lancamento = this.lancamentoService.buscarPorFuncionarioId(1L, page);    	
    	Assert.assertNotNull(lancamento);    	
    }
    
    @Test
    public void testPersistirLancamento() throws Exception{   
    	setUp();
    	Lancamento lancamento = this.lancamentoService.persistir(new Lancamento());
    	Assert.assertNotNull(lancamento);  
    }
	
}
