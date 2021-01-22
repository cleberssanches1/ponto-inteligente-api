package com.br.sanches.pontointeligente.api.dto;

import java.io.Serializable;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class FuncionarioDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7149308843517576715L;

	private Long id;

	@NotEmpty(message = "Nome não pode ser vázio.")
	@Length(min = 3, max = 200, message = "O nome deve conter entre 3 e 200 caracteres")
	private String nome;

	@NotEmpty(message = "E-mail não pode ser vázio.")
	@Length(min = 5, max = 200, message = "O e-mail deve conter entre 5 e 200 caracteres")
	@Email(message = "Email inválido")
	private String email;
	
	private Optional<String> senha  = Optional.empty();

	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();

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
	
	public Optional<String> getSenha() {
		return senha;
	}

	public void setSenha(Optional<String> senha) {
		this.senha = senha;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FuncionarioDto other = (FuncionarioDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FuncionarioDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha
				+ ", valorHora=" + valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco="
				+ qtdHorasAlmoco + "]";
	}

}
