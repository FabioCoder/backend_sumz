package edu.dhbw.ka.mwi.businesshorizon2.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 
 * Diese Klasse wird nicht mehr verwendet! Wird von EmailConfig abgelöst...
 *
 */
public class MailSenderConfig {
	/**
	 * Diese Methode gibt ein JavaMailSender Objekt zurück, welches das Versenden von Emails über GMX ermöglicht.
	 * @return GMXMailSender
	 */
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmx.de");
	    mailSender.setPort(587);
	     
	    mailSender.setUsername("sumz1718@gmx.de");
	    mailSender.setPassword("sumzisumz");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}
}
