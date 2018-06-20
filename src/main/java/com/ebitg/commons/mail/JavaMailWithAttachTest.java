package com.ebitg.commons.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaMailWithAttachTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	private static String host = "smtp.163.com";
	private static String from = "ljoa2018@163.com";
	private static String password = "123456aA";

	public static void main(String[] args) throws Exception {
		String to = "721509868@qq.com";
		Properties prop = new Properties();
		prop.setProperty("mail.host", host);
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		// ʹ��JavaMail�����ʼ���5������
		// 1������session
		Session session = Session.getInstance(prop);
		// ����Session��debugģʽ�������Ϳ��Բ鿴��������Email������״̬
		session.setDebug(true);
		// 2��ͨ��session�õ�transport����
		Transport ts = session.getTransport();
		// 3�������ʼ�������
		ts.connect(host, from, password);
		// 4�������ʼ�
		Message message = createAttachMail(session, to);
		// 5�������ʼ�
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}

	/**
	 * @Method: createAttachMail
	 * @Description: ����һ����������ʼ�
	 * @Anthor:No87
	 *
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createAttachMail(Session session, String to) throws Exception {
		MimeMessage message = new MimeMessage(session);
		// �����ʼ��Ļ�����Ϣ
		// ������
		message.setFrom(new InternetAddress(from));
		// �ռ���
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		// �ʼ�����
		message.setSubject("JavaMail�ʼ����Ͳ���");

		// �����ʼ����ģ�Ϊ�˱����ʼ����������������⣬��Ҫʹ��charset=UTF-8ָ���ַ�����
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("ʹ��JavaMail�����Ĵ��������ʼ�", "text/html;charset=UTF-8");
		// �����ʼ�����
		MimeBodyPart attach = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource("D:/test.csv"));
		attach.setDataHandler(dh);
		attach.setFileName(dh.getName());
		// ���������������ݹ�ϵ
		MimeMultipart mp = new MimeMultipart();
		mp.addBodyPart(text);
		mp.addBodyPart(attach);
		mp.setSubType("mixed");
		message.setContent(mp);
		message.saveChanges();
		// ��������Emailд�뵽E�̴洢
		// message.writeTo(new FileOutputStream("D:\\attachMail.eml"));
		// �������ɵ��ʼ�
		return message;
	}
}
