package com.fredtm.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

	private MessageDigest messageDigest;

	public String toHash(String password) {
		try {
			return calculate(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new IllegalStateException(
					"Erro ao processar algorítmo de hashing");
		}
	}

	private String calculate(String password) throws NoSuchAlgorithmException {
		messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(password.getBytes());

		byte byteData[] = messageDigest.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}

}
