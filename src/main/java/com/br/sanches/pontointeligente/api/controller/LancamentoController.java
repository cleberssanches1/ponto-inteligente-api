package com.br.sanches.pontointeligente.api.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.sanches.pontointeligente.api.dto.LancamentoDto;
import com.br.sanches.pontointeligente.api.entity.Funcionario;
import com.br.sanches.pontointeligente.api.entity.Lancamento;
import com.br.sanches.pontointeligente.api.enums.TipoEnum;
import com.br.sanches.pontointeligente.api.response.Response;
import com.br.sanches.pontointeligente.api.services.FuncionarioService;
import com.br.sanches.pontointeligente.api.services.LancamentoService;
import com.sun.el.parser.ParseException;

/**
 * Lançamentos
 * 
 * @author cleber
 *
 */
@RestController
@RequestMapping("api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {

	private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private LancamentoService lancamentoService;
	@Autowired
	private FuncionarioService funcionarioService;

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	public LancamentoController() {
		super();
	}

	@GetMapping(value = "/funcionario/{funcionarioId}")
	public ResponseEntity<Response<Page<LancamentoDto>>> listarPorFuncionarioId(
			@PathVariable("funcionarioId") Long funcionarioId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {

		Response<Page<LancamentoDto>> response = new Response<Page<LancamentoDto>>();

		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);

		Page<Lancamento> lancamentos = this.lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest);

		Page<LancamentoDto> lancamentosDto = lancamentos.map(lancamento -> this.converterLancamentoDto(lancamento));

		response.setData(lancamentosDto);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<Response<LancamentoDto>> listarPorId(@PathVariable("id") Long id) {

		log.info("buscando lancamento por id: {}", id);

		Response<LancamentoDto> response = new Response<LancamentoDto>();

		Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Lançamento não encontrada para o id: {} ", id);
			response.setErrors(new ArrayList<String>());
			response.getErrors().add("Empresa não encontrada para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterLancamentoDto(lancamento.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Adicionar
	 * 
	 * @param lancamentoDto LancamentoDto
	 * @param result        BindingResult
	 * @return ResponseEntity<Response<LancamentoDto>>
	 * @throws ParseException Exception
	 */
	@PostMapping
	public ResponseEntity<Response<LancamentoDto>> adicionar(@Valid @RequestBody LancamentoDto lancamentoDto,
			BindingResult result) throws Exception {
		log.info("adicionando lancamento : {}", lancamentoDto.toString());

		Response<LancamentoDto> response = new Response<LancamentoDto>();

		validarFuncionario(lancamentoDto, result);

		Lancamento lancamento = converterDtoParaLancamento(lancamentoDto, result);

		if (result.hasErrors()) {
			log.info("Erro validando lançamento: {} ", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		lancamento = this.lancamentoService.persistir(lancamento);

		response.setData(this.converterLancamentoDto(lancamento));

		return ResponseEntity.ok(response);
	}

	/**
	 * Atualizando o lançamento
	 * 
	 * @param id            Long
	 * @param lancamentoDto LancamentoDto
	 * @param result        BindingResult
	 * @return ResponseEntity<Response<LancamentoDto>>
	 * @throws ParseException Exception
	 * @throws Exception
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Response<LancamentoDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody LancamentoDto lancamentoDto, BindingResult result) throws Exception {

		log.info("Atualizando lancamento : {}", lancamentoDto.toString());

		Response<LancamentoDto> response = new Response<LancamentoDto>();

		validarFuncionario(lancamentoDto, result);

		lancamentoDto.setId(Optional.of(id));

		Lancamento lancamento = converterDtoParaLancamento(lancamentoDto, result);

		if (result.hasErrors()) {
			log.info("Erro validando lançamento: {} ", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		lancamento = this.lancamentoService.persistir(lancamento);

		response.setData(this.converterLancamentoDto(lancamento));

		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo lancamento : {}", id);

		Response<String> response = new Response<String>();
		Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Lançamento não encontrada para o id: {} deve ser válido. ", id);
			response.setErrors(new ArrayList<String>());
			response.getErrors().add("Erro ao remover o lançamento. Registro ão encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.lancamentoService.remover(id);
		return ResponseEntity.ok(response);
	}

	/**
	 * 
	 * @param lancamentoDto LancamentoDto
	 * @param result        BindingResult
	 * @return Lancamento
	 * @throws java.text.ParseException Exception
	 */
	private Lancamento converterDtoParaLancamento(@Valid LancamentoDto lancamentoDto, BindingResult result)
			throws Exception {

		Lancamento lancamento = new Lancamento();

		if (lancamentoDto.getId().isPresent()) {
			Optional<Lancamento> lanc = this.lancamentoService.buscarPorId(lancamentoDto.getId().get());
			if (lanc.isPresent()) {
				lancamento = lanc.get();
			} else {
				result.addError(new ObjectError("lancamento", "Lançamento não encontrado"));
			}
		} else {
			lancamento.setFuncionario(new Funcionario());
			lancamento.getFuncionario().setId(lancamentoDto.getFuncionarioId());
		}

		lancamento.setData(this.dateFormat.parse(lancamentoDto.getData()));
		lancamento.setDescricao(lancamentoDto.getDescricao());
		lancamento.setLocalizacao(lancamentoDto.getLocalizacao());
		lancamento.setTipo(TipoEnum.valueOf(lancamentoDto.getTipo()));

		if (EnumUtils.isValidEnum(TipoEnum.class, lancamentoDto.getTipo())) {
			lancamento.setTipo(TipoEnum.valueOf(lancamentoDto.getTipo()));
		} else {
			result.addError(new ObjectError("tipo", "Tipo inválido. "));
		}

		return lancamento;
	}

	private void validarFuncionario(@Valid LancamentoDto lancamentoDto, BindingResult result) {
		if (lancamentoDto.getFuncionarioId() == null) {
			result.addError(new ObjectError("funcionario", "Funcionário não informado"));
			return;
		}

		Optional<Funcionario> funcionario = funcionarioService.buscarPorId(lancamentoDto.getFuncionarioId());

		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "funcionário não encontrado."));
		}
	}

	/**
	 * Converter Lancamento Dto
	 * 
	 * @param lancamento Lancamento
	 * @return LancamentoDto
	 */
	private LancamentoDto converterLancamentoDto(Lancamento lancamento) {

		LancamentoDto lancamentoDto = new LancamentoDto();

		lancamentoDto.setId(Optional.of(lancamento.getId()));
		lancamentoDto.setData(this.dateFormat.format(lancamento.getData()));
		lancamentoDto.setTipo(lancamento.getTipo().toString());
		lancamentoDto.setDescricao(lancamento.getDescricao());
		lancamentoDto.setLocalizacao(lancamento.getLocalizacao());
		lancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());

		return lancamentoDto;
	}

}
