package com.br.sanches.pontointeligente.api.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaUtilsTest {

	private static final String SENHA = "123456";
	private final BCryptPasswordEncoder bcEncoder = new BCryptPasswordEncoder();
	
	@Test
	public void testSenhaNulo() {
		assertNull(SenhaUtils.gerarBCrypt(null));
	}
	
	@Test
	public void testGerarSenha() {
		String hash = SenhaUtils.gerarBCrypt(SENHA);
		assertTrue(bcEncoder.matches(SENHA, hash));
	}
		
}
