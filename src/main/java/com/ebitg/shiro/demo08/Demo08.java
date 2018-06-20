package com.ebitg.shiro.demo08;

import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Assert;
import org.junit.Test;

public class Demo08 {
	/**
	 * Base64
	 */
	@Test
	public void test01() {
		String str = "hello";
		String base64Encoded = Base64.encodeToString(str.getBytes());
		Assert.assertEquals("aGVsbG8=", base64Encoded);
		String str2 = Base64.decodeToString(base64Encoded);
		Assert.assertEquals(str, str2);
	}

	/**
	 * Hex
	 */
	@Test
	public void test02() {
		String str = "hello";
		String base64Encoded = Hex.encodeToString(str.getBytes());
		Assert.assertEquals("68656c6c6f", base64Encoded);
		String str2 = new String(Hex.decode(base64Encoded.getBytes()));
		Assert.assertEquals(str, str2);
	}

	/**
	 * MD5
	 */
	@Test
	public void test03() {
		String str = "hello";
		String salt = "123";
		String md5 = new Md5Hash(str, salt).toString();// 还可以转换为 toBase64()/toHex()
		Assert.assertEquals("86fcb4c0551ea48ede7df5ed9626eee7", md5);
	}

	/**
	 * SHA256
	 */
	@Test
	public void test04() {
		String str = "hello";
		String salt = "123";
		String sha1 = new Sha256Hash(str, salt).toString();
		Assert.assertEquals("7dfe54ea69b2d07a597952e49374a1aebf3c10689444a83f0a084761c8a1c626", sha1);
	}

	/**
	 * AES
	 */
	@Test
	public void test05() {
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(128); // 设置key长度
		// 生成key
		Key key = aesCipherService.generateNewKey();
		String text = "hello";
		// 加密
		String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
		System.out.println("AES加密结果" + encrptText);
		// 解密
		String text2 = new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());
		Assert.assertEquals(text, text2);
	}

	/**
	 * 用MD5生成密码
	 */
	@Test
	public void testGeneratePassword() {
		String algorithmName = "md5";
		String username = "liu";
		String password = "123";
		String salt1 = username;
		String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
		int hashIterations = 2;

		SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
		String encodedPassword = hash.toHex();
		System.out.println(salt2);
		System.out.println(encodedPassword);
	}
}
