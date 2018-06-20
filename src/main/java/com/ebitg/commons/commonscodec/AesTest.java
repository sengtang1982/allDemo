package com.ebitg.commons.commonscodec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

public class AesTest {
	private Cipher cipher;

	String content = "282602108";
	String password = "DF89n09!)>&L*F4s";
	String iv = "H(<ct&6P%k12}:@>";

	@Test
	public void test01() {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			String out = Base64.getEncoder().encodeToString(result);
			System.out.println(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}

	static final String KEY_ALGORITHM = "AES";
	static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
	/* 
	 *  
	 */
	static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
	/*
	 * AES/CBC/NoPadding 要求 密钥必须是16字节长度的；Initialization vector (IV) 必须是16字节
	 * 待加密内容的字节长度必须是16的倍数，如果不是16的倍数，就会出如下异常： javax.crypto.IllegalBlockSizeException:
	 * Input length not multiple of 16 bytes
	 * 
	 * 由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整
	 * 
	 * 可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n， 其它情况下加密数据长
	 * 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]， 除了NoPadding填充之外的任何方
	 * 式，加密数据长度都等于16*(n+1).
	 */
	static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";

	static SecretKey secretKey;

	@Test
	public void test02() throws Exception {
		cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
		// KeyGenerator 生成aes算法密钥
		secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		System.out.println("密钥的长度为：" + secretKey.getEncoded().length);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIV()));// 使用加密模式初始化 密钥
		byte[] encrypt = cipher.doFinal(content.getBytes()); // 按单部分操作加密或解密数据，或者结束一个多部分操作。

		System.out.println("method3-加密：" + Arrays.toString(encrypt));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(getIV()));// 使用解密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(encrypt);
		String out = Base64.getEncoder().encodeToString(decrypt);
		System.out.println(out);
	}
	/***
	 * 该方式可用于公交查询..............
	 * @throws Exception
	 */
	@Test
	public void test03() throws Exception {
		cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
		secretKey = new SecretKeySpec(password.getBytes("utf-8"), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIV()));
		byte[] encrypt = cipher.doFinal(content.getBytes(), 0, content.getBytes().length);
		String out = Base64.getEncoder().encodeToString(encrypt);
		System.out.println(out);
	}

	byte[] getIV() {
		return iv.getBytes();
	}
}
