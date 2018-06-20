package com.ebitg.sms;

/**
 * Created by bingone on 15/12/16.
 */
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ����http�ӿڵ�java�������ʾ�� ����Apache HttpClient 4.3
 *
 * @author songchao
 * @since 2015-04-03
 */

public class YunPianSms {

	// ���˻���Ϣ��http��ַ
	private static String URI_GET_USER_INFO = "https://sms.yunpian.com/v2/user/get.json";

	// ����ƥ��ģ�巢�ͽӿڵ�http��ַ
	private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";

	// ģ�巢�ͽӿڵ�http��ַ
	private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";

	// ����������֤��ӿڵ�http��ַ
	private static String URI_SEND_VOICE = "https://voice.yunpian.com/v2/voice/send.json";

	// �����С����й�ϵ�Ľӿ�http��ַ
	private static String URI_SEND_BIND = "https://call.yunpian.com/v2/call/bind.json";

	// ������С����й�ϵ�Ľӿ�http��ַ
	private static String URI_SEND_UNBIND = "https://call.yunpian.com/v2/call/unbind.json";

	// �����ʽ�����ͱ����ʽͳһ��UTF-8
	private static String ENCODING = "UTF-8";

	public static void main(String[] args) throws IOException, URISyntaxException {
		String apikey = "e79f9d122a27b00a164614d2edc979a9";
		String mobile = "15098577747";
		System.out.println(YunPianSms.getUserInfo(apikey));
		String text = "��kobe���Ի������Ķ�����ţ�No87,������Ϣ��";
		System.out.println(YunPianSms.sendSms(apikey, text, mobile));
	}

	/**
	 * ȡ�˻���Ϣ
	 *
	 * @return json��ʽ�ַ���
	 * @throws java.io.IOException
	 */

	public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		return post(URI_GET_USER_INFO, params);
	}

	/**
	 * ����ƥ��ģ��ӿڷ�����
	 *
	 * @param apikey
	 *            apikey
	 * @param text
	 *            ��������
	 * @param mobile
	 *            ���ܵ��ֻ���
	 * @return json��ʽ�ַ���
	 * @throws IOException
	 */

	public static String sendSms(String apikey, String text, String mobile) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("text", text);
		params.put("mobile", mobile);
		return post(URI_SEND_SMS, params);
	}

	/**
	 * ͨ��ģ�巢�Ͷ���(���Ƽ�)
	 *
	 * @param apikey
	 *            apikey
	 * @param tpl_id
	 *            ģ��id
	 * @param tpl_value
	 *            ģ�����ֵ
	 * @param mobile
	 *            ���ܵ��ֻ���
	 * @return json��ʽ�ַ���
	 * @throws IOException
	 */

	public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("tpl_id", String.valueOf(tpl_id));
		params.put("tpl_value", tpl_value);
		params.put("mobile", mobile);
		return post(URI_TPL_SEND_SMS, params);
	}

	/**
	 * ͨ���ӿڷ���������֤��
	 * 
	 * @param apikey
	 *            apikey
	 * @param mobile
	 *            ���յ��ֻ���
	 * @param code
	 *            ��֤��
	 * @return
	 */

	public static String sendVoice(String apikey, String mobile, String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("mobile", mobile);
		params.put("code", code);
		return post(URI_SEND_VOICE, params);
	}

	/**
	 * ͨ���ӿڰ������к���
	 * 
	 * @param apikey
	 *            apikey
	 * @param from
	 *            ����
	 * @param to
	 *            ����
	 * @param duration
	 *            ��Чʱ������λ����
	 * @return
	 */

	public static String bindCall(String apikey, String from, String to, Integer duration) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("from", from);
		params.put("to", to);
		params.put("duration", String.valueOf(duration));
		return post(URI_SEND_BIND, params);
	}

	/**
	 * ͨ���ӿڽ��������к���
	 * 
	 * @param apikey
	 *            apikey
	 * @param from
	 *            ����
	 * @param to
	 *            ����
	 * @return
	 */
	public static String unbindCall(String apikey, String from, String to) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("from", from);
		params.put("to", to);
		return post(URI_SEND_UNBIND, params);
	}

	/**
	 * ����HttpClient 4.3��ͨ��POST����
	 *
	 * @param url
	 *            �ύ��URL
	 * @param paramsMap
	 *            �ύ<������ֵ>Map
	 * @return �ύ��Ӧ
	 */

	public static String post(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity, ENCODING);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}
}