package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IUserPasswordResetService;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IUserPasswordResetTokenRepository;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppUserDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.UserPasswordResetTokenDao;


/**
 * This Class is used to create PasswordResetToken
 */
@Service
public class UserPasswordResetService implements IUserPasswordResetService {
	
	@Autowired
	IUserPasswordResetTokenRepository userPasswordResetTokenRepository;

	/**
	 * Creates the Token used to Reset the passowrd
	 * @param user The User for which the password Token should be created
	 * @return The Token
	 * @throws NoSuchAlgorithmException
	 */
	public UserPasswordResetTokenDao createUserPasswordResetToken(AppUserDao user) throws NoSuchAlgorithmException {
		
		Long userId = user.getAppUserId();

		//Expiration Date = Now + 1 Day
		LocalDateTime expirationDate = LocalDateTime.now(); 
		expirationDate = expirationDate.withNano(0);
		expirationDate = expirationDate.withSecond(0);
		expirationDate = expirationDate.plusDays(1); 
		
		String key = "SUMZ1718";
		key += (user.getEmail().toString());
		key += (user.getAppUserId().toString()); 
		key += (expirationDate.toString());	
		
		MessageDigest md5 = MessageDigest.getInstance("MD5");

		//Encrypting
		md5.update(key.getBytes());
		byte[] digest = md5.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}	
		
		key = sb.toString();
		
		UserPasswordResetTokenDao userPasswordResetToken = new UserPasswordResetTokenDao(null, user, expirationDate, key);
		userPasswordResetToken = userPasswordResetTokenRepository.save(userPasswordResetToken);
		
		return userPasswordResetToken; 
	}
}
