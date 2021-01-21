package com.br.sanches.pontointeligente.api.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sanches.pontointeligente.api.dto.CadastroPJDto;
import com.br.sanches.pontointeligente.api.entity.Empresa;
import com.br.sanches.pontointeligente.api.entity.Funcionario;
import com.br.sanches.pontointeligente.api.enums.PerfilEnum;
import com.br.sanches.pontointeligente.api.response.Response;
import com.br.sanches.pontointeligente.api.services.EmpresaServices;
import com.br.sanches.pontointeligente.api.services.FuncionarioService;
import com.br.sanches.pontointeligente.api.utils.SenhaUtils;

/**
 * Classe controller de acesso a recursos do sistema
 * @author cleber
 *
 */
@RestController
@RequestMapping("api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaServices empresaServices;

	/**
	 * Construtor
	 */
	public CadastroPJController() {
		super();
	}

	/**
	 * Método para cadastrar funcionário
	 * @param cadastroPJDto CadastroPJDto
	 * @param result BindingResult
	 * @return ResponseEntity<Response<CadastroPJDto>>
	 * @throws NoSuchAlgorithmException Exception
	 */
	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastar(@Valid @RequestBody CadastroPJDto cadastroPJDto,
			BindingResult result) throws NoSuchAlgorithmException {

		log.info("Cadastrando PJ: {}", cadastroPJDto.toString());

		Response<CadastroPJDto> response = new Response<CadastroPJDto>();

		validarDadosExistentes(cadastroPJDto, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPJDto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJDto, result);

		if (result.hasErrors()) {
			log.info("Erro validando dados de cadastro PJ: {} ", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		empresa = this.empresaServices.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);

		response.setData(this.converterCadastroPJDto(funcionario));

		return ResponseEntity.ok(response);
	}

	/**
	 * Converte entidade em DTO
	 * @param funcionario Funcionario
	 * @return CadastroPJDto
	 */
	private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
		CadastroPJDto cadastroPJDto = new CadastroPJDto();
		
		cadastroPJDto.setId(funcionario.getId());
		cadastroPJDto.setNome(funcionario.getNome());
		cadastroPJDto.setEmail(funcionario.getEmail());
		cadastroPJDto.setCpf(funcionario.getCpf());
		cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());
			
		return cadastroPJDto;		
	}

	/**
	 * Método para converter dto para entidade
	 * @param cadastroPJDto CadastroPJDto
	 * @param result BindingResult
	 * @return Funcionario
	 */
	private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto, BindingResult result) {
		
		Funcionario funcionario = new Funcionario();
		
		funcionario.setNome(cadastroPJDto.getNome());
		funcionario.setEmail(cadastroPJDto.getEmail());
		funcionario.setCpf(cadastroPJDto.getCpf());
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		funcionario.setSenha(SenhaUtils.gerarBCrypt(cadastroPJDto.getSenha()));
		
		return funcionario;
	}

	/**
	 *  Converte dto para empresa
	 * @param cadastroPJDto CadastroPJDto
	 * @return Empresa
	 */
	private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto) {
		Empresa empresa = new Empresa();	
		
		empresa.setCnpj(cadastroPJDto.getCnpj());
		empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
		
		return empresa;
	}

	/**
	 * valida se os Dados Existentem
	 * @param cadastroPJDto
	 * @param result
	 */
	private void validarDadosExistentes(CadastroPJDto cadastroPJDto, BindingResult result) {
		this.empresaServices.buscarPorCnpj(cadastroPJDto.getCnpj())
				.ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente")));

		this.funcionarioService.buscarPorCpf(cadastroPJDto.getCpf())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já existente")));

		this.funcionarioService.buscarPorEmail(cadastroPJDto.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "E-mail já existente")));
	}

}
