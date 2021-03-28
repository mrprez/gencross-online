package com.mrprez.gencross.online.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Service;

@Service
public class HashingService {
	
	private SecureRandom secureRandom = new SecureRandom();
	
	
	public byte[] hash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = new byte[16];
		secureRandom.nextBytes(salt);
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		return factory.generateSecret(spec).getEncoded();
	}

}
