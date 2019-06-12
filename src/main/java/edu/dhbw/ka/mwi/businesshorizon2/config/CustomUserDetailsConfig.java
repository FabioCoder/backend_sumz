package edu.dhbw.ka.mwi.businesshorizon2.config;

/**
 * Erweiterung des Users Objekts um eine User ID 
 */
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetailsConfig extends User{
	
	private static final long serialVersionUID = 1914961795626200101L;
	private final long userID;
	
	/**
	 * 
	 * @param Benutzername
	 * @param Passwort
	 * @param Rolle des Benutzers
	 * @param User-ID
	 */
	public CustomUserDetailsConfig(String username, String password, Collection<? extends GrantedAuthority> authorities, long userID) {
		super(username, password, authorities);
		this.userID = userID;
	}


	public long getUserID() {
		return userID;
	}
}