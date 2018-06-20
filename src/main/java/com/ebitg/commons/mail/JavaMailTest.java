package com.ebitg.commons.mail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaMailTest {
	public static void main(String[] args) {
		String to = "13863861926@163.com";
		String subject = "This is the Subject Line!";
		String text = "This is actual message";

		String from = "13863861926@163.com";
		Properties props = System.getProperties();
		String host = "smtp.163.com";
		// �����ʼ�������
		props.setProperty("mail.transport.protocol", "smtp"); // ʹ�õ�Э�飨JavaMail�淶Ҫ��
		props.setProperty("mail.smtp.host", host); // �����˵������ SMTP ��������ַ
		props.setProperty("mail.smtp.auth", "true");
		// ��ȡĬ��session����
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "821212");
			}
		});
		try {
			// ����Ĭ�ϵ� MimeMessage ����
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(text);
			MimeBodyPart mbpFile = new MimeBodyPart();
//			FileDataSource fds = new FileDataSource(new File("D:/a.txt"));
//			mbpFile.setDataHandler(new DataHandler(fds));
			MimeMultipart mp = new MimeMultipart();
//			mp.addBodyPart(mbpFile);
//			message.setContent(mp);
//			mp.setSubType("");
//			Transport.send(message, "13863861926@163.com", "821212");
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
