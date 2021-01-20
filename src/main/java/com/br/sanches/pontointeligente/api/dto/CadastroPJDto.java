package com.br.sanches.pontointeligente.api.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;



/**
 * 
 * @author cleber
 *
 */
public class CadastroPJDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4451712078222963372L;

	public CadastroPJDto() {
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
	
	@NotEmpty(message = "Razão social não pode ser vazio")
	@Length(min = 5, max = 200, message = "O Razao Social deve conter entre 5 e 200 caracteres")
	private String razaoSocial;
	
	@NotEmpty(message = "CNPJ não pode ser vázio.")
	@CNPJ(message="CNPJ inválido")
	private String cnpj;
	
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
		CadastroPJDto other = (CadastroPJDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CadastroPJDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + "]";
	}
		
}
