package it.lucarasconi.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Crypt {

	public static String encrypt(String plainTextPassword) throws Exception {
		// only the first 8 Bytes of the constructor argument are used
		// as material for generating the keySpec
		DESKeySpec keySpec = new DESKeySpec("".getBytes("UTF8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(keySpec);

		// ENCODE plainTextPassword String
		byte[] cleartext = plainTextPassword.getBytes("UTF8");

		Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return Base64.encodeBase64String(cipher.doFinal(cleartext));
	}

	public static String decrypt(String encryptedBase64Password) throws Exception {
		byte[] encrypedPwdBytes = Base64.decodeBase64(encryptedBase64Password);
		DESKeySpec keySpec = new DESKeySpec("".getBytes("UTF8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(keySpec);

		Cipher cipher = Cipher.getInstance("DES");// cipher is not thread safe
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainTextPwdBytes = (cipher.doFinal(encrypedPwdBytes));
		return new String(plainTextPwdBytes, "UTF-8");
	}
}
