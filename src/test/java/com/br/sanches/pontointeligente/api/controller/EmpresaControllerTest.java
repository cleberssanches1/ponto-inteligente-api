package com.br.sanches.pontointeligente.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.br.sanches.pontointeligente.api.entity.Empresa;
import com.br.sanches.pontointeligente.api.services.EmpresaServices;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private EmpresaServices empresaServices;

	private static final String BUSCAR_EMPRESA_CNPJ_URL = "/api/empresas/cnpj/";
	private static final Long ID = Long.valueOf(1L);
	private static final String RAZAO_SOCIAL = "empresa XYZ";
	private static final String CNPJ = "51463645000100";
	
	@Test
	@WithMockUser
	public void testBuscarEmpresaCnpjInvalido() throws Exception {
		BDDMockito.given(this.empresaServices.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ)
				.accept(org.springframework.http.MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors").value("Empresa não encontrada para o CNPJ " + CNPJ));
	}

	@Test
	@WithMockUser
	public void testBuscarEmpresaCnpjValido() throws Exception {
		BDDMockito.given(this.empresaServices.buscarPorCnpj(Mockito.anyString()))
				.willReturn(Optional.of(obterDadosEmpresa()));

		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ)
				.accept(org.springframework.http.MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.razaoSocial").value(RAZAO_SOCIAL))
				.andExpect(jsonPath("$.data.cnpj").value(CNPJ))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresaDto = new Empresa();

		empresaDto.setId(1L);
		empresaDto.setCnpj(CNPJ);
		empresaDto.setRazaoSocial(RAZAO_SOCIAL);

		return empresaDto;
	}

}
