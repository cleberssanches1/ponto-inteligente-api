package com.br.sanches.pontointeligente.api.dto;

import java.io.Serializable;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

/**
 * DTO
 * @author cleber
 *
 */
public class CadastroPFDto  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 300311192722284652L;

	public CadastroPFDto() {		
		super();		
	}
	
    private Long id;
	
	@NotEmpty(message = "Nome não pode ser vázio.")
	@Length(min = 3, max = 200, message = "O nome deve conter entre 3 e 200 caracteres")
	private String nome;
	
	@NotEmpty(message = "E-mail não pode ser vázio.")
	@Length(min = 5, max = 200, message = "O e-mail deve conter entre 5 e 200 caracteres")
	@Email(message="Email inválido")
	private String email;
	
	@NotEmpty(message = "Senha não pode ser vázio.")
	private String senha;
	
	@NotEmpty(message = "CPF não pode ser vázio.")
	@CPF(message="CPF inválido")
	private String cpf;
	
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();

	@NotEmpty(message = "Razão social não pode ser vazio")
	@Length(min = 5, max = 200, message = "O Razao Social deve conter entre 5 e 200 caracteres")
	private String razaoSocial;
	
	@NotEmpty(message = "CNPJ não pode ser vázio.")
	@CNPJ(message="CNPJ inválido")
	private String cnpj;
		
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Optional<String> getValorHora() {
		return valorHora;
	}
	public void setValorHora(Optional<String> valorHora) {
		this.valorHora = valorHora;
	}
	public Optional<String> getQtdHorasTrabalhoDia() {
		return qtdHorasTrabalhoDia;
	}
	public void setQtdHorasTrabalhoDia(Optional<String> qtdHorasTrabalhoDia) {
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}
	public Optional<String> getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}
	public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}
	
	@Override
	public String toString() {
		return "CadastroPFDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", valorHora=" + valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco="
				+ qtdHorasAlmoco + "]";
	}	
	
}
