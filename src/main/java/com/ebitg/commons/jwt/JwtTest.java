package com.ebitg.commons.jwt;

import io.jsonwebtoken.*;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;

public class JwtTest {
	private static final String PRIVATE_KEY = "123456789";

	@Test
	public void jwtTest() throws InterruptedException {
		// ����3������
		String jwt = this.buildJwt("abcdef��������", DateTime.now().plusSeconds(3).toDate());
		System.out.println("jwt���:" + jwt);
		// ��֤token�Ƿ����

		// Thread.sleep(4000);
		boolean isOk = this.isJwtValid(jwt);
		System.out.println(isOk);
	}

	public String buildJwt(String source, Date exp) {
		String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)// SECRET_KEY�Ǽ����㷨��Ӧ����Կ������ʹ�ö���HS256�����㷨
				.setExpiration(exp)// expTime�ǹ���ʱ��
				.claim("key", source)// �÷�������JWT�м���ֵΪvaule��key�ֶ�
				.compact();
		return jwt;
	}

	public boolean isJwtValid(String jwt) {
		// ����JWT�ַ����е����ݣ����������������֤
		Jws<Claims> claimJwts = Jwts.parser().setSigningKey(PRIVATE_KEY)// SECRET_KEY�Ǽ����㷨��Ӧ����Կ��jjwt�����Զ��жϻ����㷨
				.parseClaimsJws(jwt);
		System.out.println(claimJwts);
		JwsHeader header = claimJwts.getHeader();
		System.out.println(header);
		Claims claims = claimJwts// jwt��JWT�ַ���
				.getBody();
		String vaule = claims.get("key", String.class);// ��ȡ�Զ����ֶ�key
		// �ж��Զ����ֶ��Ƿ���ȷ
		if ("abcdef��������".equals(vaule)) {
			return true;
		} else {
			return false;
		}
		// // �ڽ���JWT�ַ���ʱ�������Կ����ȷ���������ʧ�ܣ��׳�SignatureException�쳣��˵����JWT�ַ�����α���
		// // �ڽ���JWT�ַ���ʱ�����������ʱ���ֶΡ��Ѿ����ڵ�ǰʱ�䣬�����׳�ExpiredJwtException�쳣��˵�����������Ѿ�ʧЧ
		// catch (SignatureException | ExpiredJwtException e) {
		// return false;
		// }
	}
}