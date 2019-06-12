package edu.dhbw.ka.mwi.businesshorizon2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmailConfig {
	
	//SMTP-Server
    @Value
    ("${spring.mail.host}")
    String host;
    
    //Port des SMTP Servers
    @Value
    ("${spring.mail.port}")
    Integer port;
    
    //Username SMTP Server
    @Value
    ("${spring.mail.username}")
    String username;
    
    //Passwort SMTP Server
    @Value
    ("${spring.mail.password}")
    String password;    
    
    //weitere Einstellungen für den Mailversand
    @Value
    ("${spring.mail.properties.mail.smtp.auth}")
    Boolean smtpAuth;
    
    @Value
    ("${spring.mail.properties.mail.smtp.starttls.enable}")
    Boolean starttlsEnable; 
    
    @Value 
    ("${spring.mail.properties.mail.smtp.starttls.required}")
    Boolean starttlsRequired;

    /**
     * 
     * @return SMTP-Server
     */
	public String getHost() {
		return host;
	}
	
	/**
	 * 
	 * @param SMTP-Server
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * 
	 * @return Port des SMTP-Servers
	 */
	public Integer getPort() {
		return port;
	}
	
	/**
	 * 
	 * @param Port des SMTP-Servers
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * 
	 * @return Nutzername für Konto des SMTP-Servers
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param Nutzername für Konto des SMTP-Servers
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return Passwort für Konto des SMTP-Servers
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 
	 * @param Passwort für Konto des SMTP-Servers
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 
	 * @return smtpAuth
	 */
	public Boolean getSmtpAuth() {
		return smtpAuth;
	}

	/**
	 * 
	 * @param smtpAuth
	 */
	public void setSmtpAuth(Boolean smtpAuth) {
		this.smtpAuth = smtpAuth;
	}
	
	/**
	 * 
	 * @return starttlsEnable
	 */
	public Boolean getStarttlsEnable() {
		return starttlsEnable;
	}

	/**
	 * 
	 * @param starttlsEnable
	 */
	public void setStarttlsEnable(Boolean starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}
	
	/**
	 * 
	 * @return starttlsRequired
	 */
	public Boolean getStarttlsRequired() {
		return starttlsRequired;
	}

	/**
	 * 
	 * @param starttlsRequired
	 */
	public void setStarttlsRequired(Boolean starttlsRequired) {
		this.starttlsRequired = starttlsRequired;
	} 
}
