package zhan.utils; 

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	public static void sendMail(String email,String subject, String emailMsg)
			throws AddressException, MessagingException {
		// 1.����һ���������ʼ��������Ự���� Session
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");
		props.setProperty("mail.host", "smtp.163.com"); //�����ʼ���������ַ
		props.setProperty("mail.smtp.auth", "true");// ָ����֤Ϊtrue

		// ������֤��
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				//��֤�������˺ź���Ȩ����
				return new PasswordAuthentication("zhangzhan0611", "zhan0611");
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.����һ��Message�����൱�����ʼ�����
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("zhangzhan0611@163.com")); // ���÷�����

		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // ���÷��ͷ�ʽ�������

		message.setSubject(subject); //subject���ʼ�����
		// message.setText("����һ�⼤���ʼ�����<a href='#'>���</a>");
		message.setContent(emailMsg, "text/html;charset=utf-8");

		// 3.���� Transport���ڽ��ʼ�����
		Transport.send(message);
	}
}