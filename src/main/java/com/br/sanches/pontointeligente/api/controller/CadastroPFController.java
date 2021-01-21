package com.br.sanches.pontointeligente.api.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

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

import com.br.sanches.pontointeligente.api.dto.CadastroPFDto;
import com.br.sanches.pontointeligente.api.entity.Empresa;
import com.br.sanches.pontointeligente.api.entity.Funcionario;
import com.br.sanches.pontointeligente.api.enums.PerfilEnum;
import com.br.sanches.pontointeligente.api.response.Response;
import com.br.sanches.pontointeligente.api.services.EmpresaServices;
import com.br.sanches.pontointeligente.api.services.FuncionarioService;
import com.br.sanches.pontointeligente.api.utils.SenhaUtils;


@RestController
@RequestMapping("api/cadastrar-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaServices empresaServices;

	/**
	 * Construtor
	 */
	public CadastroPFController() {
		super();
	}

	@PostMapping
	public ResponseEntity<Response<CadastroPFDto>> cadastar(@Valid @RequestBody CadastroPFDto cadastroPFDto,
			BindingResult result) throws NoSuchAlgorithmException {

		log.info("Cadastrando PF: {}", cadastroPFDto.toString());

		Response<CadastroPFDto> response = new Response<CadastroPFDto>();

		validarDadosExistentes(cadastroPFDto, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPFDto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPFDto, result);

		if (result.hasErrors()) {
			log.info("Erro validando dados de cadastro PJ: {} ", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		empresa = this.empresaServices.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);

		response.setData(this.converterCadastroPFDto(funcionario));

		return ResponseEntity.ok(response);
	}

	/**
	 * Converte entidade em DTO
	 * @param funcionario Funcionario
	 * @return CadastroPJDto
	 */
	private CadastroPFDto converterCadastroPFDto(Funcionario funcionario) {
		CadastroPFDto cadastroPFDto = new CadastroPFDto();
		
		cadastroPFDto.setId(funcionario.getId());
		cadastroPFDto.setNome(funcionario.getNome());
		cadastroPFDto.setEmail(funcionario.getEmail());
		cadastroPFDto.setCpf(funcionario.getCpf());
		cadastroPFDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastroPFDto.setCnpj(funcionario.getEmpresa().getCnpj());
					
		funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHorasAlmoco -> cadastroPFDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));		
		funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(qtdHorasTrabalhoDia -> cadastroPFDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabalhoDia))));		
		funcionario.getValorHoraOpt().ifPresent(valorHora -> cadastroPFDto.setValorHora(Optional.of(valorHora.toString())));
		
		
		return cadastroPFDto;		
	}

	/**
	 * Método para converter dto para entidade
	 * @param cadastroPJDto CadastroPJDto
	 * @param result BindingResult
	 * @return Funcionario
	 */
	private Funcionario converterDtoParaFuncionario(CadastroPFDto cadastroPFDto, BindingResult result) {
		
		Funcionario funcionario = new Funcionario();
		
		funcionario.setNome(cadastroPFDto.getNome());
		funcionario.setEmail(cadastroPFDto.getEmail());
		funcionario.setCpf(cadastroPFDto.getCpf());
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(SenhaUtils.gerarBCrypt(cadastroPFDto.getSenha()));
		
		cadastroPFDto.getQtdHorasAlmoco().ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		cadastroPFDto.getQtdHorasTrabalhoDia().ifPresent(qtdHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalhoDia)));		
		cadastroPFDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
		
		return funcionario;
	}

	/**
	 *  Converte dto para empresa
	 * @param cadastroPJDto CadastroPJDto
	 * @return Empresa
	 */
	private Empresa converterDtoParaEmpresa(CadastroPFDto cadastroPFDto) {
		Empresa empresa = new Empresa();	
		
		empresa.setCnpj(cadastroPFDto.getCnpj());
		empresa.setRazaoSocial(cadastroPFDto.getRazaoSocial());
		
		return empresa;
	}

	/**
	 * valida se os Dados Existentem
	 * @param cadastroPJDto
	 * @param result
	 */
	private void validarDadosExistentes(CadastroPFDto cadastroPFDto, BindingResult result) {
	
		this.empresaServices.buscarPorCnpj(cadastroPFDto.getCnpj())
				.ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente")));

		this.funcionarioService.buscarPorCpf(cadastroPFDto.getCpf())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já existente")));

		this.funcionarioService.buscarPorEmail(cadastroPFDto.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "E-mail já existente")));
		
	}
	
}
