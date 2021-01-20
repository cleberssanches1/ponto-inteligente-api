package com.br.sanches.pontointeligente.api;


import static org.junit.Assert.assertEquals;

import org.junit.After;
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
class PontoInteligenteApplicationTests {

	@Test
	void contextLoads() {
	}

	
}
