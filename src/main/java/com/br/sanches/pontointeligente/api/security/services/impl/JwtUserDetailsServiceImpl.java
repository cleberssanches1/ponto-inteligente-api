package com.br.sanches.pontointeligente.api.security.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.sanches.pontointeligente.api.entity.Funcionario;
import com.br.sanches.pontointeligente.api.security.JwtUserFactory;
import com.br.sanches.pontointeligente.api.security.entities.Usuario;
import com.br.sanches.pontointeligente.api.security.services.UsuarioService;
import com.br.sanches.pontointeligente.api.services.FuncionarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

//	@Autowired
//	private UsuarioService usuarioService;
	
	@Autowired
	private FuncionarioService funcionarioService;

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<Usuario> funcionario = usuarioService.buscarPorEmail(username);
//
//		if (funcionario.isPresent()) {
//			return JwtUserFactory.create(funcionario.get());
//		}
//
//		throw new UsernameNotFoundException("Email não encontrado.");
//	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorEmail(username);

		if (funcionario.isPresent()) {
			return JwtUserFactory.create(funcionario.get());
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}

}
