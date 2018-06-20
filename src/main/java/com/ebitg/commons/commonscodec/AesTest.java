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
			Cipher cipher = Cipher.getInstance("AES");// ����������
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// ��ʼ��
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
	 * AES/CBC/NoPadding Ҫ�� ��Կ������16�ֽڳ��ȵģ�Initialization vector (IV) ������16�ֽ�
	 * ���������ݵ��ֽڳ��ȱ�����16�ı������������16�ı������ͻ�������쳣�� javax.crypto.IllegalBlockSizeException:
	 * Input length not multiple of 16 bytes
	 * 
	 * ���ڹ̶���λ�������Զ��ڱ��������������ĵ�, �ӡ����ܲ�����
	 * 
	 * �� �Կ�������ԭʼ���ݳ���Ϊ16������n��ʱ������ԭʼ���ݳ��ȵ���16*n����ʹ��NoPaddingʱ���ܺ����ݳ��ȵ���16*n�� ��������¼������ݳ�
	 * �ȵ���16*(n+1)���ڲ���16��������������£�����ԭʼ���ݳ��ȵ���16*n+m[����mС��16]�� ����NoPadding���֮����κη�
	 * ʽ���������ݳ��ȶ�����16*(n+1).
	 */
	static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";

	static SecretKey secretKey;

	@Test
	public void test02() throws Exception {
		cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
		// KeyGenerator ����aes�㷨��Կ
		secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		System.out.println("��Կ�ĳ���Ϊ��" + secretKey.getEncoded().length);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIV()));// ʹ�ü���ģʽ��ʼ�� ��Կ
		byte[] encrypt = cipher.doFinal(content.getBytes()); // �������ֲ������ܻ�������ݣ����߽���һ���ಿ�ֲ�����

		System.out.println("method3-���ܣ�" + Arrays.toString(encrypt));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(getIV()));// ʹ�ý���ģʽ��ʼ�� ��Կ
		byte[] decrypt = cipher.doFinal(encrypt);
		String out = Base64.getEncoder().encodeToString(decrypt);
		System.out.println(out);
	}
	/***
	 * �÷�ʽ�����ڹ�����ѯ..............
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
