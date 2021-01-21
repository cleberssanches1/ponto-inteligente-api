package com.br.sanches.pontointeligente.api.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sanches.pontointeligente.api.dto.EmpresaDto;
import com.br.sanches.pontointeligente.api.entity.Empresa;
import com.br.sanches.pontointeligente.api.response.Response;
import com.br.sanches.pontointeligente.api.services.EmpresaServices;

@RestController
@RequestMapping("api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {
		
	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);

	@Autowired
	private EmpresaServices empresaServices;
	
	public EmpresaController() {		
	}
	
	@GetMapping(value = "/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> cadastar(@PathVariable("cnpj") String cnpj) {
		log.info("buscando empresa por cnpj: {}", cnpj);
		
		Response<EmpresaDto> response = new Response<EmpresaDto>();
		Optional<Empresa> empresa = empresaServices.buscarPorCnpj(cnpj);
		
		if (!empresa.isPresent()) {
			log.info("Empresa não encontrada para o CNPJ: {} ", cnpj);
			
			response.setErrors(new ArrayList<String>());
			response.getErrors().add("Empresa não encontrada para o CNPJ "+ cnpj);		
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.convertEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Conversor
	 * @param empresa Empresa
	 * @return EmpresaDto
	 */
	private EmpresaDto convertEmpresaDto(Empresa empresa) {
		EmpresaDto empresaDto = new EmpresaDto();
		
		empresaDto.setId(empresa.getId());
		empresaDto.setCnpj(empresa.getCnpj());
		empresaDto.setRazaoSocial(empresa.getRazaoSocial());
		
		return empresaDto;
	}
	
	
}
