import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestAA {
	public void test01() {
		String str = "进货单总单ID，细单ID，数量，供应商id，供应商名称，货品ID，货品名称，货品单位， 规格，厂家名称，批号，采购员id，采购员姓名，生产日期，有效期，发货人ID，发货人名称";
		str = str.replace('，', '|').replace(" ", "");
		str = String.format("select * from SYS_COLUMN_CN where  regexp_like(cnname,'.*%s.*')", str);
		System.out.println(str);
	}

	@Test
	public void testParseJson() {
		String str = "[{\"ID\":\"1448\",\"车牌\":\"鲁FV2639\",\"站\":\"3292\",\"GPSX\":\"121.437875\",\"GPSY\":\"37.48363\",\"站内外\":\"0\"},{\"ID\":\"1449\",\"车牌\":\"鲁FV2650\",\"站\":\"192\",\"GPSX\":\"121.441963\",\"GPSY\":\"37.471557\",\"站内外\":\"0\"},{\"ID\":\"1447\",\"车牌\":\"鲁FV2665\",\"站\":\"193\",\"GPSX\":\"121.437465\",\"GPSY\":\"37.465782\",\"站内外\":\"1\"}]";
		JSONArray parse = JSON.parseArray(str);
		for (int i = 0; i < parse.size(); i++) {
			JSONObject jsonObject = parse.getJSONObject(i);
			System.out.println(jsonObject);
			Set<String> keys = jsonObject.keySet();
			keys.forEach(k -> {
				String v = jsonObject.getString(k);
				System.out.println(k + ":" + v);
			});
		}
	}

	@Test
	public void test02() throws ClientProtocolException, IOException, InterruptedException {
		while (true) {
			String HOST = "http://223.99.174.234:4997";
			String URL_GETRANDOM = HOST + "/busposition.asmx/get_random?";
			String password = "DF89n09!)>&L*F4s";
			String iv = "H(<ct&6P%k12}:@>";
			// 创建Get方法实例
			String content = get(URL_GETRANDOM);
			String stationId = "10095";
			String lineId = "5";
			String lineStatus = "1";
			String userRole = "0";
			String attach = "1";
			String strSession = encrypt(content, password, iv);
			String strFlag = "GONGAN";
			String strIMEI = "ABC";
			String URL_GETBUSLINESTATES = String.format(HOST + "/busposition.asmx/GetBusLineStatusEncry?"
					+ "stationID=%s&lineID=%s&lineStatus=%s&userRole=%s&attach=%s&strSession=%s&strFlag=%s&strIMEI=%s",
					stationId, lineId, lineStatus, userRole, attach, strSession, strFlag, strIMEI);
			String result = get(URL_GETBUSLINESTATES);
			System.out.println(result);
			Thread.sleep(10000);
		}
	}

	private String get(String url) throws IOException, ClientProtocolException {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		HttpResponse response = httpclient.execute(get);
		HttpEntity entity = response.getEntity();
		String respStr = IOUtils.toString(entity.getContent(), "UTF-8");
		System.out.println(respStr);
		Pattern pattern = Pattern.compile("<string xmlns=\"http://www.dongfang-china.com/\">(.*)</string>");
		Matcher matcher = pattern.matcher(respStr);
		String content = null;
		if (matcher.find()) {
			content = matcher.group(1);
		}
		return content;
	}

	public String encrypt(String content, String password, String iv) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec secretKey = new SecretKeySpec(password.getBytes("utf-8"), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));
			byte[] encrypt = cipher.doFinal(content.getBytes(), 0, content.getBytes().length);
			return Base64.getEncoder().encodeToString(encrypt);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException("加密错误", e);
		}
	}
}
