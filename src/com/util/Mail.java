package com.util;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mail {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Mail.class);
    
	// ���巢���ˡ��ռ��ˡ�SMTP���������û������롢���⡢���ݵ�
	private String displayName;
	private String to;
	private String from;
	private String smtpServer;
	private String smtpPort;
	private String username;
	private String password;
	private String subject;
	private String content;
	private boolean ifAuth; // �������Ƿ�Ҫ�����֤
	private String filename = "";
	@SuppressWarnings("rawtypes")
	private Vector file = new Vector(); // ���ڱ��淢�͸������ļ���ļ���

	/**
	 * ����SMTP��������ַ
	 */
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}
	
	/**
	 * ����SMTP�������˿�
	 */
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	/**
	 * ���÷����˵ĵ�ַ
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * ������ʾ�����
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * ���÷������Ƿ���Ҫ�����֤
	 */
	public void setIfAuth(boolean ifAuth) {
		this.ifAuth = ifAuth;
	}

	/**
	 * ����E-mail�û���
	 */
	public void setUserName(String username) {
		this.username = username;
	}

	/**
	 * ����E-mail����
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * ���ý�����
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * ��������
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * ������������
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * �÷��������ռ�������
	 */
	@SuppressWarnings("unchecked")
	public void addAttachfile(String fname) {
		file.addElement(fname);
	}

	public Mail() {

	}

	/**
	 * ��ʼ��SMTP��������ַ��������E-mail��ַ���û������롢�����ߡ����⡢����
	 */
	public Mail(String smtpServer,String smtpPort, String from, String displayName,
			String username, String password, String to, String subject,
			String content) {
		this.smtpServer = smtpServer;
		this.smtpPort = smtpPort;
		this.from = from;
		this.displayName = displayName;
		this.ifAuth = true;
		this.username = username;
		this.password = password;
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	/**
	 * ��ʼ��SMTP��������ַ��������E-mail��ַ�������ߡ����⡢����
	 */
	public Mail(String smtpServer,String smtpPort, String from, String displayName, String to,
			String subject, String content) {
		this.smtpServer = smtpServer;
		this.smtpPort = smtpPort;
		this.from = from;
		this.displayName = displayName;
		this.ifAuth = false;
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	/**
	 * �����ʼ�
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap send() {
		HashMap map = new HashMap();
		map.put("state", "success");
		String message = "�ʼ����ͳɹ���";
		Session session = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.port", smtpPort);
		if (ifAuth) { // ��������Ҫ�����֤
			props.put("mail.smtp.auth", "true");
			SmtpAuth smtpAuth = new SmtpAuth(username, password);
			session = Session.getDefaultInstance(props, smtpAuth);
		} else {
			props.put("mail.smtp.auth", "false");
			session = Session.getDefaultInstance(props, null);
		}
		session.setDebug(false);
		Transport trans = null;
		try {
			Message msg = new MimeMessage(session);
			try {
				Address from_address = new InternetAddress(from, displayName);
				msg.setFrom(from_address);
			} catch (java.io.UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage());
			}
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent(content.toString(), "text/html;charset=gb2312");
			mp.addBodyPart(mbp);
			if (!file.isEmpty()) {// �и���
				Enumeration efile = file.elements();
				while (efile.hasMoreElements()) {
					mbp = new MimeBodyPart();
					filename = efile.nextElement().toString(); // ѡ���ÿһ��������
					FileDataSource fds = new FileDataSource(filename); // �õ����Դ
					mbp.setDataHandler(new DataHandler(fds)); // �õ��������?����BodyPart
					mbp.setFileName(fds.getName()); // �õ��ļ���ͬ������BodyPart
					mp.addBodyPart(mbp);
				}
				file.removeAllElements();
			}
			msg.setContent(mp); // Multipart���뵽�ż�
			msg.setSentDate(new Date()); // �����ż�ͷ�ķ�������
			// �����ż�
			msg.saveChanges();
			trans = session.getTransport("smtp");
			trans.connect(smtpServer, username, password);
			trans.sendMessage(msg, msg.getAllRecipients());
			trans.close();
		} catch (AuthenticationFailedException e) {
			map.put("state", "failed");
			message = "�ʼ�����ʧ�ܣ�����ԭ��\n" + "�����֤����!";
			LOGGER.error(e.getMessage());
		} catch (MessagingException e) {
			message = "�ʼ�����ʧ�ܣ�����ԭ��\n" + e.getMessage();
			map.put("state", "failed");
			LOGGER.error(e.getMessage());
			Exception ex = null;
			if ((ex = e.getNextException()) != null) {
				LOGGER.error(e.getMessage());
			}
		}
		map.put("message", message);
		return map;
	}
	
	class SmtpAuth extends javax.mail.Authenticator {
		private String username, password;

		public SmtpAuth(String username, String password) {
			this.username = username;
			this.password = password;
		}

		protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
			return new javax.mail.PasswordAuthentication(username, password);
		}
	}
}