package com.br.sanches.pontointeligente.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//import org.slf4j.Logger;
/**
 * Encripta a senha
 * @author cleber
 *
 */
public class SenhaUtils {

	
	//private static final Logger log = org.slf4j.LoggerFactory.getLogger(SenhaUtils.class);
	/**
	 * Criptografa
	 * @param senha
	 * @return
	 */
	public static String gerarBCrypt(String senha) {
		if(senha == null) {
			return senha;
		}
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();		
		return bCrypt.encode(senha);			
	}	
	
	/**
	 * Verifica se esta correto.
	 * @param senha
	 * @param senhaEncoded
	 * @return
	 */
	public static boolean senhaValida(String senha, String senhaEncoded) {		
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();		
		return bCrypt.matches(senha, senhaEncoded);	
	}	
	
}
