package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import java.time.LocalDateTime;

/**
 * DTO for sending and receiving the PasswordResetToken
 */
public class UserPasswordResetTokenDto {
	/**
	 * Id
	 */
	private Long userPasswordResetTokenId;
	/**
	 * Id of the user owning it
	 */
	private Long appUserId;
	/**
	 * Expiration Date
	 */
	private LocalDateTime expirationDate;
	/**
	 * {TODO}
	 */
	private String tokenKey;
	
	public UserPasswordResetTokenDto(Long userPasswordResetTokenId, Long appUserId, LocalDateTime expirationDate,
			String tokenKey) {
		super();
		this.userPasswordResetTokenId = userPasswordResetTokenId;
		this.appUserId = appUserId;
		this.expirationDate = expirationDate;
		this.tokenKey = tokenKey;
	}
	
	public UserPasswordResetTokenDto() {
		
	}
	
	public Long getUserPasswordResetTokenId() { return userPasswordResetTokenId; }

	public void setUserPasswordResetTokenId(Long userPasswordResetTokenId) { this.userPasswordResetTokenId = userPasswordResetTokenId; }

	public Long getAppUserId() { return appUserId; }

	public void setAppUserId(Long appUserId) { this.appUserId = appUserId; }

	public LocalDateTime getExpirationDate() { return expirationDate; }

	public void setExpirationDate(LocalDateTime expirationDate) { this.expirationDate = expirationDate; }

	public String getTokenKey() { return tokenKey; }

	public void setTokenKey(String tokenKey) { this.tokenKey = tokenKey; }
	
	public String toString() {
		String result = ""; 
		result += " userPasswordResetTokenId: " + userPasswordResetTokenId;
		result += " appUserId: " + appUserId; 
		result += " expirationDate: " + expirationDate; 
		result += " tokenKey: " + tokenKey;
		
		return result;
	}
}
