package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppUserDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.UserPasswordResetTokenDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.AppUserDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.UserPutRequestDto;

@Service
public interface IUserService {
	
	public List<AppUserDao> findAllUsers();
    
    public AppUserDao findByEmail(String s);

	/**
	 * Activates an user.
	 * @param token Base64 - encoded Token. (The Token is the json-File of UserActivationTokenDto)
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws Exception if the Tokenid is not present in the DB || if the token is invalid
	 */
	void activateUser(String token) throws JsonParseException, JsonMappingException, IOException, Exception;

	/**
	 * Checks if the given Token is valid. If the Token is valid, no exception is thrown. If the token is invalid,
	 * an exception is thrown. A valid received token is equal to a token in the DB and the user owning it is active.
	 * @param token Base64Encoded Token of the JSON of the UserPasswordResetTokenDto-Class
	 * @return The UserPasswordResetTokenDto-Class
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws Exception if the token is invalid || if the token is expired || if the user is not active
	 */
	UserPasswordResetTokenDao checkPasswordResetToken(String token) throws JsonParseException, JsonMappingException, IOException, Exception;

	/**
	 * Sends an Email containing the Token and the link to the Reset-Service to the given address.
	 * @param email User's email adress
	 * @param host Host-URL
	 * @return Base64Encoded JSON-Token of the UserPasswordResetTokenDto-Class
	 * @throws Exception if the user is inactive
	 * @throws UsernameNotFoundException if the mail adress does not exists in the DB
	 */
	String requestUserPasswordReset(String email, String host) throws Exception;

	/**
	 * Encrypts a password using the BCryptPasswordEncoder-Class using the strength configured in
	 * security.encoding-strength
	 * @param password The password to encrypt
	 * @return The encrypted password
	 */
	String encodePassword(String password);

	/**
	 * Creates a new User
	 * @param userDto
	 * @param host the host URL
	 * @return User - DAO
	 * @throws Exception if user email already exists
	 */
	AppUserDao addUser(AppUserDto userDto, String host) throws Exception;

	/***
	 * Resets the password to the given value
	 * @param userDto The DTO for an user
	 * @param tokenStr the Base64Encoded JSON of UserPasswordResetTokenDto
	 * @throws Exception if the id of the User given in the Token is not present in the DB
	 */
	void resetUserPassword(@Valid AppUserDto userDto, String tokenStr) throws Exception;

	/**
	 * Deletes an user
	 * @param userDto needs the current password to be set
	 * @param id id of the user to be deleted
	 * @throws Exception if the passwords don't match || if the userid does not exists
	 */
	void deleteUser(@Valid AppUserDto userDto, Long id) throws Exception;

	/**
	 * Changes the password of an user
	 * @param user
	 * @param userID id of the user whose password should change
	 * @throws Exception if the old password does not eqals the current password || if the id does not exists
	 */
	void updateUserPassword(UserPutRequestDto user, Long userID) throws Exception;

	/**
	 * Maps email to id
	 * @param username  a user's email
	 * @return id mapped to the email
	 */
	Long getUserId(String username);
}
