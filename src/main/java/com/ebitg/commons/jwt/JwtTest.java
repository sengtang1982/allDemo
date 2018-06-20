package com.ebitg.commons.jwt;

import io.jsonwebtoken.*;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;

public class JwtTest {
	private static final String PRIVATE_KEY = "123456789";

	@Test
	public void jwtTest() throws InterruptedException {
		// 设置3秒后过期
		String jwt = this.buildJwt("abcdef这是明文", DateTime.now().plusSeconds(3).toDate());
		System.out.println("jwt结果:" + jwt);
		// 验证token是否可用

		// Thread.sleep(4000);
		boolean isOk = this.isJwtValid(jwt);
		System.out.println(isOk);
	}

	public String buildJwt(String source, Date exp) {
		String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)// SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法
				.setExpiration(exp)// expTime是过期时间
				.claim("key", source)// 该方法是在JWT中加入值为vaule的key字段
				.compact();
		return jwt;
	}

	public boolean isJwtValid(String jwt) {
		// 解析JWT字符串中的数据，并进行最基础的验证
		Jws<Claims> claimJwts = Jwts.parser().setSigningKey(PRIVATE_KEY)// SECRET_KEY是加密算法对应的密钥，jjwt可以自动判断机密算法
				.parseClaimsJws(jwt);
		System.out.println(claimJwts);
		JwsHeader header = claimJwts.getHeader();
		System.out.println(header);
		Claims claims = claimJwts// jwt是JWT字符串
				.getBody();
		String vaule = claims.get("key", String.class);// 获取自定义字段key
		// 判断自定义字段是否正确
		if ("abcdef这是明文".equals(vaule)) {
			return true;
		} else {
			return false;
		}
		// // 在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
		// // 在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
		// catch (SignatureException | ExpiredJwtException e) {
		// return false;
		// }
	}
}