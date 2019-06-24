package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IUserActivationService;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IUserActivationTokenRepository;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.UserActivationTokenDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppUserDao;

/**
 * This Class is used to create ActivationToken
 */
@Service
public class UserActivationService implements IUserActivationService {
	
	@Autowired 
	private IUserActivationTokenRepository userActivationTokenRepository;

	/**
	 * Creates the UserActivationTokenDao
	 * @param appUser The user for which the ActivationToken should be created
	 * @return An UserActivationTokenDao for the given User
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public UserActivationTokenDao createUserActivationToken(AppUserDao appUser) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		Long userId = appUser.getAppUserId();
		
		LocalDateTime expirationDate = LocalDateTime.now(); 
		expirationDate = expirationDate.withNano(0); 
		expirationDate = expirationDate.withSecond(0);
		//Expiration Date = CurrentTime + 1 Day
		expirationDate = expirationDate.plusDays(1); 
				
		String tokenKey = "SUMZ1718";
		tokenKey += (appUser.getEmail().toString());
		tokenKey += (appUser.getAppUserId().toString()); 
		tokenKey += (expirationDate.toString());	
		
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		//Encrypting tokenKey
		md5.update(tokenKey.getBytes());
		byte[] digest = md5.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}	
		
		tokenKey = sb.toString();
		
		UserActivationTokenDao userActivationToken = new UserActivationTokenDao(null, appUser, expirationDate, tokenKey); 
		userActivationToken = userActivationTokenRepository.save(userActivationToken);
		
		return userActivationToken; 
	}
	
}
