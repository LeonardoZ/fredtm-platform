package com.fredtm.api.email;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class Mail {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${send.from.email}")
	private String fromEmail;

	public boolean sendMail(String to, String subject, String msg) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings("unchecked")
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(to);
				message.setFrom(new InternetAddress(Mail.this.fromEmail));
				message.setSubject(subject);
				message.setSentDate(new Date());
				@SuppressWarnings("rawtypes")
				Map model = new HashMap();
				model.put("newMessage", msg);
				message.setText(msg, true);
			}
		};
		try {
			mailSender.send(preparator);
			return true;
		} catch (MailException ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
