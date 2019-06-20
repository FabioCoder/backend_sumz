package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import java.time.LocalDateTime;

/**
 * DTO for sending and receiving the activation token
 */
public class UserActivationTokenDto {
	/**
	 * The token's id
	 */
	private Long userActivationTokenId;
	/**
	 * The id of the user owning the token (The user-profile, which gets activated by the token)
	 */
	private Long appUserId;
	/**
	 * Expiration date of the token
	 */
	private LocalDateTime expirationDate;
	/**
	 * {TODO}
	 */
	private String tokenKey;
	
	public UserActivationTokenDto(Long userActivationTokenId, Long appUserId, LocalDateTime expirationDate,
			String tokenKey) {
		super();
		this.userActivationTokenId = userActivationTokenId;
		this.appUserId = appUserId;
		this.expirationDate = expirationDate;
		this.tokenKey = tokenKey;
	}
	
	public UserActivationTokenDto() {}
	
	public Long getUserActivationTokenId() { return userActivationTokenId; }

	public void setUserActivationTokenId(Long userActivationTokenId) { this.userActivationTokenId = userActivationTokenId; }

	public Long getAppUserId() { return appUserId; }

	public void setAppUserId(Long appUserId) { this.appUserId = appUserId; }

	public LocalDateTime getExpirationDate() { return expirationDate; }

	public void setExpirationDate(LocalDateTime expirationDate) { this.expirationDate = expirationDate; }

	public String getTokenKey() { return tokenKey; }

	public void setTokenKey(String tokenKey) { this.tokenKey = tokenKey; }

}
